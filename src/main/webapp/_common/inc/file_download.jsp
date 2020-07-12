<%@page import="java.io.File, java.io.*"%><%

    // 다운받을 파일의 이름을 가져옴
	String filePath = request.getParameter("filePath");
    String fileName = request.getParameter("fileName");
    String strfileName = request.getParameter("strfileName");
 
  	
    // 실제 내보낼 파일명
    String orgfilename = fileName;  
  
    // 다운받을 파일이 저장되어 있는 폴더 이름
  	String saveFolder = filePath;
  
    // Context에 대한 정보를 알아옴
  	ServletContext context = getServletContext(); // 서블릿에 대한 환경정보를 가져옴
  
    // 실제 파일이 저장되어 있는 폴더의 경로 :: request.getSession().getServletContext().getRealPath("/")
  	String realFolder = context.getRealPath(saveFolder);
	//application.log("filePath=" + filePath);
  	//application.log("fileName=" + fileName);
  	//application.log("strfileName=" + strfileName);
  	//application.log("realFolder=" + realFolder);
  	//application.log("context.getRealPath()=" + context.getRealPath("/"));
  	//application.log("request.getContextPath()="+request.getContextPath());
  	//application.log("request.getRequestURI()"+request.getRequestURI());
  	
  	//String realFile = request.getSession().getServletContext().getRealPath("/") + filePath + java.io.File.separator + strfileName;
  	String realFile = realFolder + strfileName;
  	application.log("realFile=" + realFile);
  	
  	String client = ""; 
  
  	try{
		// 다운받을 파일을 불러옴
		File file = new File(realFile);
		byte b[] = new byte[4096];
		
		client = request.getHeader("User-Agent");
		
		// page의 ContentType등을 동적으로 바꾸기 위해 초기화시킴
		response.reset();
		//response.setContentType("application/octet-stream");
		response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
		
		// 한글 인코딩
		//String Encoding = new String(fileName.getBytes("UTF-8"), "8859_1");
		// 파일 링크를 클릭했을 때 다운로드 저장 화면이 출력되게 처리하는 부분
		//response.setHeader("Content-Disposition", "attachment; filename = " + Encoding);
		if(client.indexOf("MSIE") != -1){
            response.setHeader ("Content-Disposition", "attachment; filename="+new String(orgfilename.getBytes("KSC5601"),"ISO8859_1"));
        } else {
            // 한글 파일명 처리
            orgfilename = new String(orgfilename.getBytes("utf-8"),"iso-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
        } 
		
		// 파일의 세부 정보를 읽어오기 위해서 선언
		FileInputStream in = new FileInputStream(realFile);
		
		// 파일에서 읽어온 세부 정보를 저장하는 파일에 써주기 위해서 선언
		ServletOutputStream out2 = response.getOutputStream();
		
		int numRead;
		// 바이트 배열 b의 0번 부터 numRead번 까지 파일에 써줌 (출력)
		while((numRead = in.read(b, 0, b.length)) != -1){
			out2.write(b, 0, numRead);
		}
		application.log("file writting completed.");
		
		in.close();
		out2.flush();
		out2.close();
		
		application.log("All I/ Stream Closed.");
   
  	} catch(Exception e){
  		//application.log("exception=" + e.getMessage());
  	}
 %>