// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AdmBonNoticeController.java

package kr.caincheon.church.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import kr.caincheon.church.admin.ParControllerConst;
import kr.caincheon.church.admin.PopupControllerConst;
import kr.caincheon.church.church.AlbControllerConst;
import kr.caincheon.church.church.ChurchControllerConst;
import kr.caincheon.church.common.base.CommonController;
import kr.caincheon.church.gyogu.GyoguConst;
import kr.caincheon.church.gyogu.MsgControllerConst;
import kr.caincheon.church.gyogu.OldGyogujangControllerConst;
import kr.caincheon.church.intro.HistoryControllerConst;
import kr.caincheon.church.news.NewsControllerConst;
import kr.caincheon.church.samok.CureControllerConst;

/**
 * CKEdtiro 내 파일 업로드를 처리 한다.
 * @author benjamin
 */
@Controller
public class CKEditorFileUploadController extends CommonController implements AdmBonNoticeControllerConst
{
	//private final org.apache.log4j.Logger _logger = org.apache.log4j.Logger.getLogger(getClass());

	/*
	 * 공통처리
	 * - 업로드시 parameter로 구분
	 */
	@RequestMapping(value = "/ckeditor/fileUploadInContent.do")
    public void imageUploadInCKEditor(HttpServletRequest request, HttpServletResponse response, MultipartFile upload)
    		throws ServletException, IOException, Exception {
		String uploadPath = request.getParameter("uploadPath").replace("upload/ckeditor/", "");
		while(uploadPath.charAt(0) == '/') {
			uploadPath = uploadPath.substring(1);
		}
		fileUploadHandleInCKEditor (request, response, upload, uploadPath);
    }

	/*
	 * 교구장동정
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent01.do")
    public void imageUploadInCKEditorboard_01(HttpServletRequest request, HttpServletResponse response, MultipartFile upload)
    		throws ServletException, IOException, Exception {
		fileUploadHandleInCKEditor (request, response, upload, ParControllerConst.LEFT_MENU_DATA_PG); // board_01
    }

	/*
	 * 교회/교구/공동체 소식
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent02.do")
	public void imageUploadInCKEditorboard_02(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
    	fileUploadHandleInCKEditor ( request,  response,  upload, NewsControllerConst.LEFT_MENU_DATA_PG ); // board_02
    }

	/*
	 * 본당알림
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent03.do")
	public void imageUploadInCKEditorboard_03(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
    	fileUploadHandleInCKEditor ( request,  response,  upload, AdmBonNoticeControllerConst.LEFT_MENU_DATA_PG ); // board_03
    }
	
	/*
	 * 사목자료
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent04.do")
	public void imageUploadInCKEditorboard_04(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
    	fileUploadHandleInCKEditor ( request,  response,  upload, CureControllerConst.LEFT_MENU_DATA_PG ); // board_04
    }

	/*
	 * 역대교구장
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent05.do")
	public void imageUploadInCKEditorboard_05(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
    	fileUploadHandleInCKEditor ( request,  response,  upload, OldGyogujangControllerConst.LEFT_MENU_DATA_PG ); // board_05
    }
	
	/*
	 * 교구앨범
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent06.do")
	public void imageUploadInCKEditorboard_06(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
		// @RequestMapping(value = "/admin/board/albumImageUpload.do")
    	fileUploadHandleInCKEditor ( request,  response,  upload, AlbControllerConst.LEFT_MENU_DATA_PG ); // board_06
    }

	/*
	 * 본당앨범
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent07.do")
	public void imageUploadInCKEditorboard_07(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
    	fileUploadHandleInCKEditor ( request,  response,  upload, AdmChurchAlbumControllerConst.LEFT_MENU_DATA_PG ); // board_07
    }

	/*
	 * 교구영상
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent08.do")
	public void imageUploadInCKEditorboard_08(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
    	fileUploadHandleInCKEditor ( request,  response,  upload, AdmGyoguMovControllerConst.LEFT_MENU_DATA_PG ); // board_08
    }
	
	/*
	 * 게시판 관리에서 관리 하는 ckEditor 저장
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContentNew_{i_sBidx}.do")
	public void imageUploadInCKEditorboard_new(HttpServletRequest request
											, HttpServletResponse response
											, @PathVariable String i_sBidx
											, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
    	fileUploadHandleInCKEditorNew( request,  response,  upload, i_sBidx); // board_08
    }
	
	/*
	 * 팝업
	 */
	@RequestMapping(value = "/admin/board/mainImageUpload.do")
    public void imageUploadInCKEditorMainPopup(HttpServletRequest request, HttpServletResponse response, MultipartFile upload)
    		throws ServletException, IOException, Exception {
		fileUploadHandleInCKEditor (request, response, upload, PopupControllerConst.LEFT_MENU_DATA_PG);        
    }

	/*
	 * 교구장 메시지
	 */
	@RequestMapping(value = "/admin/board/etcImageUpload.do")
    public void imageUploadInCKEditorMessage(HttpServletRequest request, HttpServletResponse response, MultipartFile upload)
    		throws ServletException, IOException, Exception {
		fileUploadHandleInCKEditor (request, response, upload, MsgControllerConst.LEFT_MENU_DATA_PG);
    }

	/*
	 * 온라인역사관1
	 */
	@RequestMapping(value = "/admin/board/infoImageUpload.do")
    public void imageUploadInCKEditorInfomgnt01(HttpServletRequest request, HttpServletResponse response, MultipartFile upload)
    		throws ServletException, IOException, Exception {
		fileUploadHandleInCKEditor (request, response, upload, HistoryControllerConst.LEFT_MENU_DATA_PG);
    }

	/*
	 * 정보관리 > 교구청내용
	 */
	@RequestMapping(value = "/admin/ckeditor/fileUploadInContent09.do")
	public void imageUploadInCKEditorInfomgnt02(HttpServletRequest request, HttpServletResponse response, MultipartFile upload) 
    		throws ServletException, IOException, Exception{
    	fileUploadHandleInCKEditor ( request,  response,  upload, GyoguConst.LEFT_MENU_DATA_PG ); // gyogu .. 미합의 사항 추가 정의
    }

	/*
	 * 본당둘러보기
	 */
	@RequestMapping(value = "/admin/board/infoImageUpload2.do")
    public void lookAroundChurchImageUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload)
    		throws ServletException, IOException, Exception {
		fileUploadHandleInCKEditor (request, response, upload, ChurchControllerConst.LEFT_MENU_DATA_PG);
    }


	/*
	 * 멤버 업로드 이미지
	 */
	@RequestMapping(value = "/admin/board/memImageUpload.do")
    public void communityImageUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile upload)
    		throws ServletException, IOException, Exception {
		fileUploadHandleInCKEditor (request, response, upload, InqControllerConst.LEFT_MENU_DATA_PG);
    }

	
}
