<%@page language="java" contentType="text/html; charset=UTF-8" %><%@page 
import="com.oreilly.servlet.MultipartRequest, com.oreilly.servlet.multipart.DefaultFileRenamePolicy, java.io.*, java.util.*, java.text.SimpleDateFormat" %><%!public String pnull(String s) {
	return s==null ? "" : s;
}

public String pnull(Object s) {
	return s==null ? "" : s.toString();
}

public String pnull(java.util.Map m, String p) {
	Object s = m.get(p);
	return s==null ? "" : s.toString();
}

public void moveFile(String srcFullpathFile, String targetFullpathFile, boolean isMakeThumbnailImage) throws Exception {
	File srcFile = new File(srcFullpathFile);
    File dstFile = new File(targetFullpathFile);
    // 경로 체크
    System.out.println("[uploadOk.jsp][moveFile()] srcFile="+srcFile);
    System.out.println("[uploadOk.jsp][moveFile()] dstFile="+dstFile);
    
    // 파일명 이동
    if(!srcFile.renameTo(dstFile))
    {
    	// file copy
        byte[] buf = new byte[1024];
        FileInputStream  fin  = new FileInputStream(srcFile);
        FileOutputStream fout = new FileOutputStream(dstFile);
        int read = 0;
        try {
	        while((read = fin.read(buf, 0, buf.length))!=-1){
	            fout.write(buf, 0, read);
	        }
        } catch (Exception e) {
        	e.printStackTrace();
        	throw e;
        } finally {
	        fin.close();
	        fout.close();
        }
        // src delete
        srcFile.delete();
    }
    // thumbnail making
    if(isMakeThumbnailImage)
    	kr.caincheon.church.common.utils.ImageUtils.createThumbnail(targetFullpathFile);
}%><%

/**
 *
 * 이 프로그램은 image, pdf, mp3라는 이름으로 파일을 업로드시 처리되고, 각 1개씩 최대 3개의 업로드가 가능함.
 *
 */
	request.setCharacterEncoding("UTF-8");
	
	response.setContentType("application/json");
	response.setHeader("Content-Type", "application/json");
	
	// 응답 데이터 처리
	Map<String, String> responseData = new HashMap<String, String>();
	responseData.put("\"status\"","\"\"");
	responseData.put("\"message\"","\"\"");
	
	System.out.println("[uploadOk.jsp][0] /admin/board/uploadOk.jsp called..");
	
	// 10Mbyte 제한
    int maxSize  = 1024*1024*30;
    
    // 웹서버 컨테이너 경로
    String wwwroot = request.getSession().getServletContext().getRealPath("/");
    
    // 업로드 경로
    String uploadPath = wwwroot;
    // 업로드 파일명
    String uploadFile = "";
	// 실제 저장되는 위치
    String savePath = "";
    // 실제 저장할 파일명
    String saveFile = java.util.UUID.randomUUID().toString().replaceAll("-","");
    // file type
    
    
    int read = 0;
    byte[] buf = new byte[1024];
    FileInputStream fin = null;
    FileOutputStream fout = null;
    long currentTime = System.currentTimeMillis(); 
    SimpleDateFormat simDf = new SimpleDateFormat("yyyyMMddHHmmss"); 
    
    try {
    	
        MultipartRequest multi = new MultipartRequest(request, uploadPath, maxSize, "UTF-8", new DefaultFileRenamePolicy());
        
        // parameter
        String isKeepOriginalNm = pnull(multi.getParameter("isKeepOriginalNm")); /* 업로드 원본 이름 유지 여부 */
        String paramFilePath    = pnull(multi.getParameter("uploadPath"));
        String uploadType       = pnull(multi.getFilesystemName("uploadType"));
        String originalPhoto    = pnull(multi.getFilesystemName("photo"));
        String originalImage    = pnull(multi.getFilesystemName("image"));
        String originalPdf      = pnull(multi.getFilesystemName("pdf"));
        String originalMp3      = pnull(multi.getFilesystemName("mp3"));

        
        // check
        if(paramFilePath == null || paramFilePath.length() == 0) {
        	throw new Exception("첨부 파일의 저장 위치 정보`uploadPath`가 누락입니다.");
        }
        if(originalImage.length() == 0 && originalPdf.length() == 0 && originalMp3.length() == 0 && originalPhoto.length() == 0) {
        	throw new Exception("첨부 할 파일이 없습니다.");
        }
        
        // 파일 업로드 경로(ex : /home/tour/web/ROOT/upload)
        uploadPath = "upload/" + (paramFilePath==null || paramFilePath.length()==0 ? "etc/"  :  paramFilePath ) + "/";
        uploadPath = uploadPath.replace("//", "/").replace("\\/", "/");
        if( !(new File(wwwroot + uploadPath)).exists() ) { // target path exists check
    		new File(wwwroot + uploadPath).mkdirs();
    	}

        
        System.out.println("[uploadOk.jsp][1] wwwroot=" + wwwroot);
        System.out.println("[uploadOk.jsp][1] uploadPath=" + uploadPath);
        System.out.println("[uploadOk.jsp][1] uploadType=" + uploadType);
        System.out.println("[uploadOk.jsp][1] isKeepOriginalNm=" + isKeepOriginalNm);
        System.out.println("[uploadOk.jsp][1] originalPhoto=" + originalPhoto);
        System.out.println("[uploadOk.jsp][1] originalImage=" + originalImage);
        System.out.println("[uploadOk.jsp][1] originalPdf=" + originalPdf);
        System.out.println("[uploadOk.jsp][1] originalMp3=" + originalMp3);
        System.out.println("[uploadOk.jsp][1] saveFile=" + saveFile);
        System.out.println("[uploadOk.jsp][1] wwwroot + uploadPath=" + (wwwroot + uploadPath)); 
        
        
        // 원본이름 유지라면,,, : 4가지 케이스중 단 1개만 온다. 파일 선택시 즉시 해당 파일 업로드로 처리시 호출됨.
        if("Y".equalsIgnoreCase(isKeepOriginalNm)) {
        	if(originalPhoto.length()>0) saveFile = originalPhoto.substring(0, originalPhoto.lastIndexOf("."));
        	if(originalImage.length()>0) saveFile = originalImage.substring(0, originalImage.lastIndexOf("."));
        	if(originalPdf.length()>0) saveFile = originalPdf.substring(0, originalPdf.lastIndexOf("."));
        	if(originalMp3.length()>0) saveFile = originalMp3.substring(0, originalMp3.lastIndexOf("."));
        }
        System.out.println("[uploadOk.jsp][2] isKeepOriginalNm["+isKeepOriginalNm+"] saveFile=" + saveFile);
        
        // if a image type upload,
        if(originalImage.length() > 0) {
        	uploadType    = originalImage.lastIndexOf(".") == -1 ? ".jpg1" : originalImage.substring(originalImage.lastIndexOf(".")) ;
            String srcFullpathFile    = wwwroot + originalImage;
            String targetFullpathFile = wwwroot + uploadPath + saveFile + uploadType;
            
            // 파일명 이동
            moveFile(srcFullpathFile, targetFullpathFile, true);
            responseData.put("\"status\"","\"normal\"");
        	responseData.put("\"message\"","\"이미지가 업로드 되었습니다.\"");
        	responseData.put("\"uploaded\"", "\"/"+uploadPath + saveFile + uploadType+"\"" );
        	
        	System.out.println("[uploadOk.jsp][3] 이미지 업 완료:" + responseData);
        }
        if(originalPhoto.length() > 0) {
        	uploadType    = originalPhoto.lastIndexOf(".") == -1 ? ".jpg1" : originalPhoto.substring(originalPhoto.lastIndexOf(".")) ;
            String srcFullpathFile    = wwwroot + originalPhoto;
            String targetFullpathFile = wwwroot + uploadPath + saveFile + uploadType;
            
            // 파일명 이동
            moveFile(srcFullpathFile, targetFullpathFile, true);
            responseData.put("\"status\"","\"normal\"");
        	responseData.put("\"message\"","\"포토가 업로드 되었습니다.\"");
        	responseData.put("\"uploaded\"", "\"/"+uploadPath + saveFile + uploadType+"\"" );
        	
        	System.out.println("[uploadOk.jsp][4] 포토 업 완료:" + responseData);
        }
        
        // if a pdf type upload,
        if(originalPdf.length() > 0) {
        	uploadType    = originalPdf.lastIndexOf(".") == -1 ? ".pdf1" : originalPdf.substring(originalPdf.lastIndexOf(".")) ;
            String srcFullpathFile    = wwwroot + originalPdf;
            String targetFullpathFile = wwwroot + uploadPath + saveFile + uploadType;
            
            // 파일명 이동
            moveFile(srcFullpathFile, targetFullpathFile, false);
            responseData.put("\"status\"","\"normal\"");
        	responseData.put("\"message\"","\"PDF파일이 업로드 되었습니다.\"");
        	responseData.put("\"uploaded\"", "\"/"+uploadPath + saveFile + uploadType+"\"" );
        	
        	System.out.println("[uploadOk.jsp][5] pdf 업 완료:" + responseData);
        }
        
        // if a mp3 type upload,
        if(originalMp3.length() > 0) {
        	uploadType    = originalMp3.lastIndexOf(".") == -1 ? ".mp31" : originalMp3.substring(originalMp3.lastIndexOf(".")) ;
        	String srcFullpathFile    = wwwroot + originalMp3;
            String targetFullpathFile = wwwroot + uploadPath + saveFile + uploadType;
            
            // 파일명 이동
            moveFile(srcFullpathFile, targetFullpathFile, false);
            responseData.put("\"status\"","\"normal\"");
        	responseData.put("\"message\"","\"mp3가 업로드 되었습니다.\"");
        	responseData.put("\"uploaded\"", "\"/"+uploadPath + saveFile + uploadType+"\"" );
        	
        	System.out.println("[uploadOk.jsp][6] mp3 업 완료:" + responseData);
        }
        
//         // 전송받은 parameter의 한글깨짐 방지
//         //String title = multi.getParameter("photo");
//         //title = new String(title.getBytes("8859_1"), "UTF-8");
//         if(uploadType.equalsIgnoreCase("pdf")) {
        	
//         	// 파일업로드
//             uploadFile = multi.getFilesystemName("pdf");
            
//             // save path
//         	//savePath = root + "img\\magazine\\upload\\pdf\\";
//         	savePath = wwwroot + "/magazine/pdf/";
        	
//         	File savePathDir = new File(savePath); 
//         	if(!savePathDir.exists()) { 
//         		savePathDir.mkdirs();
//         	}
        	
//             saveFile = uploadFile;
//             System.out.println("[uploadOk.jsp][pdf] saveFile=" + saveFile);
        
//         } else if(uploadType.equalsIgnoreCase("mp3")) {
        	
//         	// 파일업로드
//             uploadFile = multi.getFilesystemName("mp3");
            
//             // save path
//         	savePath = wwwroot + "/sound/";
        	
//         	File savePathDir = new File(savePath); 
//         	if(!savePathDir.exists()) { 
//         		savePathDir.mkdirs();
//         	}
        	
//             saveFile = uploadFile;
//             System.out.println("[uploadOk.jsp][mp3] saveFile=" + saveFile);
            
//         } else {
        	
//         }
        
//         // 업로드된 파일 객체 생성
//         File oldFile = new File(wwwroot + originalImage);//uploadPath + uploadFile);
 
//         // 실제 저장될 파일 객체 생성
//         File newFile = new File(wwwroot + paramFilePath + originalImage );//savePath + saveFile);
     	
//         // 파일명 rename
//         if(!oldFile.renameTo(newFile)){
//             buf = new byte[1024];
//             fin = new FileInputStream(oldFile);
//             fout = new FileOutputStream(newFile);
//             read = 0;
//             while((read=fin.read(buf,0,buf.length))!=-1){
//                 fout.write(buf, 0, read);
//             }
//             fin.close();
//             fout.close();
            
//             // only file.
//             if(oldFile.isFile()) oldFile.delete();
//         }  
 
    } catch(Exception e) {
        e.printStackTrace();
        
        //responseData.clear();
        responseData.put("\"status\"","\"error\"");
    	responseData.put("\"message\"","\""+e.getMessage().replace("\\","/")+"\""); // replace()를 적용함으로써, 500에러는 200 으로 전송하게 됨
    	
    }

    /* Response by JSON DATA */
    System.out.print( responseData.toString().replaceAll("=",":") );
    out.print( responseData.toString().replaceAll("=",":") );
    
%>