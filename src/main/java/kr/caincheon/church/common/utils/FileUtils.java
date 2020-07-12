package kr.caincheon.church.common.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import kr.caincheon.church.common.base.CommonParameter;

public class FileUtils 
{
	//
	private CommonParameter _cparam = new CommonParameter();
	
	//
	private static class Singletone {
		private static FileUtils INSTANCE = new FileUtils();
		private static FileUtils getInstance() {
			return INSTANCE == null ? (INSTANCE=new FileUtils()) : INSTANCE; 
		}
	}
	
    //
	private FileUtils() {
	}
	
	// 
	public static FileUtils getInstance() {
		return Singletone.getInstance();
	}
	
    // 파일 업로드후 업로드된 파일 목록을 리턴한다.
    public List parseInsertFileInfo(Map map, HttpServletRequest request)
        throws Exception
    {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        Iterator iterator = multipartHttpServletRequest.getFileNames();
        MultipartFile multipartFile = null;
        String originalFileName = null;
        String originalFileExtension = null;
        String storedFileName = null;
        List rtList = new ArrayList();
        
        // check the parameters
        String user_id= map.containsKey("user_id")? (String)map.get("user_id") : "ADMIN";
        String b_idx  = map.containsKey("b_idx")  ? (String)map.get("b_idx")   : "";
        String bl_idx = map.containsKey("bl_idx") ? (String)map.get("bl_idx")  : "";// it's existing when modify mode
        String bu_idx = map.containsKey("bu_idx") ? (String)map.get("bu_idx")  : "";// it's existing when modify mode
        
        // Y 라면 원본 파일명으로 저장
        boolean isKonm = _cparam.pnull(map, "isKeepOriginalNm").equalsIgnoreCase("Y") ? true:false;
        
        //_params.put("CONTEXT_ROOT_PATH", request.getSession().getServletContext().getRealPath("/") + "/upload/church_notice/");
        //String contextRoot = request.getSession().getServletContext().getRealPath("/"), tmp = "";
        String tmp="", absolutePath = map.get("CONTEXT_ROOT_PATH").toString();//contextRoot+fileSaveCanonicalPath;
        File file = new File(absolutePath);
        if(!file.exists())
            file.mkdirs();
        while(iterator.hasNext()) 
        {
        	String uploadedFileOne = (String)iterator.next();
        	
            multipartFile = multipartHttpServletRequest.getFile(uploadedFileOne);
            if(!multipartFile.isEmpty())
            {
            	// file save
                originalFileName      = multipartFile.getOriginalFilename();
                originalFileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
                storedFileName        = isKonm ? originalFileName : UtilString.getRandomString()+originalFileExtension;
                multipartFile.transferTo(new File(absolutePath+storedFileName));
                
                // if file is a image, Make a Thumbnail Image
                tmp = storedFileName.toLowerCase();
                tmp = originalFileExtension.toLowerCase();
                if(tmp.indexOf(".gif") != -1 || tmp.indexOf(".jpg") != -1 || tmp.indexOf(".jpeg") != -1 || tmp.indexOf(".png") != -1) {
                	ImageUtils.createThumbnail(absolutePath+storedFileName);
                }
                
                Map upFileInfo = new HashMap();
                if(b_idx.length()>0)  upFileInfo.put("B_IDX",  b_idx);
                if(bl_idx.length()>0) upFileInfo.put("BL_IDX", bl_idx);
                if(bu_idx.length()>0) upFileInfo.put("BU_IDX", bu_idx);
                upFileInfo.put("IS_USE", "Y");
                upFileInfo.put("FILE_TYPE", tmp.substring(1).toUpperCase());
                upFileInfo.put("USER_ID", user_id);
                upFileInfo.put("ORIGINAL_FILE_NAME", originalFileName);
                upFileInfo.put("STORED_FILE_NAME", storedFileName);
                upFileInfo.put("FILE_SIZE", Long.valueOf(multipartFile.getSize()));
                upFileInfo.put("STORED_ABSOLUTE_PATH", absolutePath);
                rtList.add(upFileInfo);
            }
        }
        return rtList;
    }
    
    // 지정한 파일을 삭제한다.
    public boolean deleteFile(String fileFullpath, String thumbnailFileFullpath) {
    	java.io.File f = new java.io.File(fileFullpath);
    	if(f.exists()) {
    		boolean rtBool = f.delete();
    		if(rtBool && thumbnailFileFullpath!=null && thumbnailFileFullpath.length() > 0) {
    			f = new java.io.File(thumbnailFileFullpath);
    			if(f.exists()) {
    				f.delete();
    			} else {
    				_cparam.E(Thread.currentThread().getStackTrace() , "Thumbnail File is not exists.["+thumbnailFileFullpath+"]");
    			}
    		}
    		return rtBool;
    	} else {
    		_cparam.E(Thread.currentThread().getStackTrace() , "File is not exists.["+fileFullpath+"]");
    	}
    	return false;
    }
    
    
}
