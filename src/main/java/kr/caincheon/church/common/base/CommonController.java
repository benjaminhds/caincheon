// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonController.java

package kr.caincheon.church.common.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import kr.caincheon.church.admin.serivce.NBoardServiceImpl;
import kr.caincheon.church.common.utils.ImageUtils;
import kr.caincheon.church.common.utils.UtilDate;


public abstract class CommonController extends CommonService {

	public Map<String, Object> _params;
	public Map<String, Object> _session_params;

	/**
	 * Constructor
	 */
	public CommonController() {
	}

	/*
	 * request에서 Attributes를 모두 제거한다.
	 */
	protected void clearAttributes(HttpServletRequest request) {
		Enumeration emA = request.getAttributeNames();
		String k="", v="";
		while(emA.hasMoreElements()) {
			k = pnull(emA.nextElement());
			request.setAttribute(k, "");
		}
	}
	
	/**
	 * Convert a request parameters to inner map
	 * 
	 * @param request
	 */
	protected void build(HttpServletRequest request) throws Exception {
		this.build(request, true);
	}
	protected void build(HttpServletRequest request, boolean isMakeQuerystring) 
			throws Exception  {
		_params = new HashMap<String, Object>();
		_session_params = new HashMap<String, Object>();

		// request parameters
		_params.clear();
		String k = "", queryString = "";
		String v = null;
		int i = 0;
		
		//System.out.println("파람맵:"+request.getParameterMap());
		
		for (Enumeration em = request.getParameterNames(); em.hasMoreElements();) {
			k = pnull(em.nextElement());
			v = pnull(request.getParameter(k));

			_params.put(k, v);

			//
			if (v.length() > 0 && !("pageSize".equals(k) || "pageNo".equals(k))) {
				queryString = queryString + (i++ > 0 ? "&" : "") + k + "=" + v;
			}

			E(null, Thread.currentThread().getStackTrace(), " build() ==> k=" + k + ", v=" + _params.get(k));
		}

		if (isMakeQuerystring && queryString.length() > 0) {
			_params.put("queryString", queryString);
		}
		
		//
		int isAdmin = request.getRequestURI().indexOf("/admin/");
		pnullPut(_params, "pageNo", "1");
		pnullPut(_params, "pageSize", isAdmin == 0 ? "20" : "12");
		
		// header
		String referrer = request.getHeader("Referer");
		setHeader(_params, "referrer", referrer);//이전 페이지 URL
		
		// cookie
		Cookie[] cookies =request.getCookies();
		if(cookies != null && cookies.length > 0) {
			Map cm = new HashMap();
			for(Cookie cookie : cookies) {
				cm.put(cookie.getName(), cookie.getValue());
			}
			_params.put("COOKIE", cm);
		}
		
		// session check
		_session_params.clear();
		java.util.Enumeration<String> enn = request.getSession().getAttributeNames();
		while (enn.hasMoreElements()) {
			String kn = enn.nextElement();
			_session_params.put(kn, request.getSession().getAttribute(kn));

			E(null, Thread.currentThread().getStackTrace(), " CommonController.build() :: check a session attr ====> " + kn + "=" + request.getSession().getAttribute(kn) );
		}
		String ctxroot = request.getSession().getServletContext().getRealPath("/");
		_session_params.put("CONTEXT_ROOT", ctxroot.substring(0, ctxroot.length()-1));

		//
		_params.put(SESSION_MAP, _session_params);

	}

	/**
	 * 파일 업로드 처리 공통함수 - 파일 선택 즉시 업로드 하는 기능,
	 */
	protected void fileUploadHandleInCKEditor(HttpServletRequest request, HttpServletResponse response, MultipartFile upload, String left_menu_data_pg) 
	throws Exception {

		OutputStream out = null;
		PrintWriter printWriter = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String callback = request.getParameter("CKEditorFuncNum");

		try {
			// subPathName = subPathName.replace("/", "");
			String subPathName = getUploadBaseURI(left_menu_data_pg).replace("upload/", "");
			String uploadBaseURI = "upload/ckeditor/" + subPathName;
			if (!"/".equals(uploadBaseURI.substring(uploadBaseURI.length() - 1))) {
				uploadBaseURI = uploadBaseURI + "/";
			}
			uploadBaseURI = uploadBaseURI + UtilDate.getDateFormat("yyyyMMdd") + "/";
			String uploadFullPath = request.getSession().getServletContext().getRealPath("/") + uploadBaseURI;

			String origineFileFullName = upload.getOriginalFilename();
			String fileName   = origineFileFullName.substring(0, origineFileFullName.lastIndexOf("."));
			String fileExtr   = origineFileFullName.substring(origineFileFullName.lastIndexOf(".") + 1);
			String uploadFile = uploadFullPath + origineFileFullName;

			// file move
			if (!new File(uploadFullPath).exists()) { // make dir
				new File(uploadFullPath).mkdirs();
			}
			int i = 0;
			while (new File(uploadFile).exists()) {// check a dup
				fileName = fileName + "_" + i++;
				uploadFile = uploadFullPath + fileName + "." + fileExtr;
			}
			// move a file. 
			byte bytes[] = upload.getBytes();
			out = new FileOutputStream(new File(uploadFile));
			out.write(bytes);

			// thumbnaiil make :: BJM
			if (fileExtr.equalsIgnoreCase("jpg") || fileExtr.equalsIgnoreCase("jpeg")
					|| fileExtr.equalsIgnoreCase("gif") || fileExtr.equalsIgnoreCase("png")
					|| fileExtr.equalsIgnoreCase("bmp")) {
				ImageUtils.createThumbnail(uploadFile);
			}

			// response
			String fileUrl = "/" + uploadBaseURI + fileName + "." + fileExtr;
			printWriter = response.getWriter();
			printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback+ ",'" + fileUrl + "','이미지를 업로드 하였습니다.')</script>");
			printWriter.flush();

		} catch (IOException e) {
			e.printStackTrace();

			try {
				if (printWriter == null)
					printWriter = response.getWriter();
				printWriter.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction("+ callback + ",'','업로드 중 에러 발생[" + e.getMessage() + "]')</script>");
				printWriter.flush();
			} catch (IOException e1) {
			}

		} finally {
			try {
				if (out != null)
					out.close();
				if (printWriter != null)
					printWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 파일 업로드 처리 공통함수 - 파일 선택 즉시 업로드 하는 기능,
	 */
	protected List<Map<String, String>> fileUploadHandle(HttpServletRequest request, HttpServletResponse response
			, MultipartHttpServletRequest uploads, String uri_path, boolean isOverwrite) 
	throws Exception {

		List<Map<String, String>> rtList = new ArrayList<Map<String, String>>();

		OutputStream out = null;
		PrintWriter printWriter = null;
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		try {
			// upload path set
			String uploadBaseURI = "upload/" + getUploadBaseURI(uri_path).replace("upload/", "");
			if (!"/".equals(uploadBaseURI.substring(uploadBaseURI.length() - 1))) {
				uploadBaseURI = uploadBaseURI + "/";
			}

			String uploadFullPath = request.getSession().getServletContext().getRealPath("/") + uploadBaseURI;
			// check a path
			if (!new File(uploadFullPath).exists()) {
				new File(uploadFullPath).mkdirs();
			}

			// upload files
			Map<String, MultipartFile> filesMap = uploads.getFileMap();
			for (int i = 0, i2 = filesMap.size(); i < i2; i++) {
				MultipartFile upload = filesMap.get(i);
				// src
				String uploadedFilename = upload.getOriginalFilename();
				String fileName = uploadedFilename.substring(0, uploadedFilename.lastIndexOf("."));
				String fileExtr = uploadedFilename.substring(uploadedFilename.lastIndexOf(".") + 1);
				String destFile = fileName + "." + fileExtr;
				
				// target
				if (!isOverwrite) {
					while (new File(uploadFullPath + destFile).exists()) {// check a dup
						destFile = fileName + "_" + System.currentTimeMillis() + "." + fileExtr;
					}
				}
				upload.transferTo(new File(uploadFullPath + destFile));
				
				// return data set
				Map<String,String> fileMap = new HashMap<String,String>();
				fileMap.put("UPLOADED_URI", uploadFullPath);
				fileMap.put("ORIGINAL_FILENAME", uploadedFilename);
				fileMap.put("UPLOADED_FILENAME", destFile);
				rtList.add(fileMap);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
				if (printWriter != null)
					printWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return rtList;
	}

	/*
	 * NBOARD 목록조회 공통
	 */
	protected CommonDaoDTO callNBoardList(NBoardServiceImpl nBoardService, Map<String, Object> _params, ModelAndView mv,
		String left_menu_data_pg, boolean hasOrgList, int ATTACHED_FILE_COUNT) 
		throws Exception {
		
		//
		CommonDaoDTO rtDto = null;

		//
		//pnullPut(_params, "pageSize", "20");// _params.put("pageSize", "20");
		try {
			// 조직 조회 파라메터 체크
			if (hasOrgList) {
				if (!_params.containsKey("LV1")) {
					throw new CommonException("조직 조회를 위해서는 Controller or Service에서 LV1 (K,V) 대한 설정이 필요합니다.", "ERR-987");
				}
				pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");// 본당조회
			}

			// NBOARD 조회
			pnullPut(_params, "TOP_COUNT", "100");
			int attachedFileCount = ipnull(_params, "_ATTACHED_FILE_COUNT", ATTACHED_FILE_COUNT);
			rtDto = nBoardService.nboardList(_params, left_menu_data_pg, hasOrgList, attachedFileCount);

		} catch (Exception e) {
			throw e;
		}
		if (rtDto == null)
			rtDto = new CommonDaoDTO();

		//
		mv.addObject("_params", _params);
		mv.addObject("LIST", rtDto.daoList);
		mv.addObject("paging", rtDto.paging);
		if (hasOrgList)
			mv.addObject("_1x00xList", rtDto.orgList);

		return rtDto;
	}

	/*
	 * NBOARD 상세보기 위한 조회
	 */
	protected CommonDaoDTO callNBoardContents(NBoardServiceImpl nBoardService, Map<String, Object> _params, ModelAndView mv,
		String left_menu_data_pg, boolean hasOrgList, int ATTACHED_FILE_COUNT)
	throws Exception {
		//
		CommonDaoDTO rtDto = new CommonDaoDTO();

		//
		try {
			// 조직 조회
			if (hasOrgList) {
				if (!_params.containsKey("LV1")) {
					throw new CommonException("조직 조회를 위해서는 LV1에 대한 설정이 필요합니다.", "ERR-987");
				}
				pnullPut(_params, "LV1", "02' AND LV2!='00' AND LV3!='000");// 본당조회
			}
			// NBOARD 조회
			int attachedFileCount = ipnull(_params, "_ATTACHED_FILE_COUNT", ATTACHED_FILE_COUNT);
			rtDto = nBoardService.nboardContents(_params, left_menu_data_pg, true, attachedFileCount);
		} catch (CommonException e) {
			e.printStackTrace();
		}

		//
		mv.addObject("_params", _params);
		mv.addObject("CONTENTS", rtDto.daoDetailContent);
		mv.addObject("paging", rtDto.paging);
		if (hasOrgList)
			mv.addObject("_1x00xList", rtDto.orgList);

		return rtDto;
	}

	/*
	 * JSON 응답이 필요시 기본적인 포멧의 json 응답이 가능한 map을 리턴
	 */
	protected Map<String, Object> getJson(boolean isSuccess) {
		return getJson(isSuccess, "요청이 정상 처리 되었습니다.");
	}

	/*
	 * JSON 응답이 필요시 기본적인 포멧의 json 응답이 가능한 map을 리턴
	 */
	protected Map<String, Object> getJson(boolean isSuccess, String sucess_message) {
		/*
		 * public @ResponseBody Map<String, Object> OP() {
		 *     Map<String, Object> json = getJson(true);
		 *     return json;
		 * }
		 */
		Map<String, Object> json = new HashMap<String, Object>();
		
		if(isSuccess) {
        	json.put(JSON_STATUS, JSON_STATUS_SECCESS);
        	json.put(JSON_MESSAGE, (sucess_message==null || sucess_message.length()==0 ? "요청이 정상 처리 되었습니다." : sucess_message));
        } else {
        	json.put(JSON_STATUS, JSON_STATUS_FAILURE);
        	json.put(JSON_MESSAGE, "요청이 실패 되었습니다.");
        }
		
		return json;
	}
	protected final String JSON_MESSAGE = "message";
	protected final String JSON_STATUS  = "status";
	protected final String JSON_STATUS_SECCESS = "success";
	protected final String JSON_STATUS_FAILURE = "fail";
	
	
}
