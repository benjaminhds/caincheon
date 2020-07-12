<%@ page session="false" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
 
<%@ page import="java.io.*"%>
<%@ page import="java.text.*" %>
<%@ page import="java.lang.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
 
 
<%
	out.println("filedown.jsp");
application.log("filedown.jsp");

	request.setCharacterEncoding("UTF-8"); 
 
    // 파일 업로드된 경로
    String root = request.getSession().getServletContext().getRealPath("/");
    application.log("root="+ root);
    //String savePath = root + "upload";
 
 	// 다운받을 파일의 이름을 가져옴
    String filePath = "C:\\Workspace\\mbs@caincheon\\src\\main\\webapp\\" + request.getParameter("filePath");
    application.log("filePath="+ filePath);
    String fileName = request.getParameter("fileName");
    application.log("fileName="+ fileName);
     
    // 실제 내보낼 파일명
    String orgfilename = fileName;      
 
    InputStream in = null;
    OutputStream os = null;
    File file = null;
    boolean skip = false;
    String client = ""; 
 
    try{       
        // 파일을 읽어 스트림에 담기
        try{
            file = new File(filePath, fileName);
            in = new FileInputStream(file);
        }catch(FileNotFoundException fe){
            skip = true;
        }
          
        client = request.getHeader("User-Agent");
 
        // 파일 다운로드 헤더 지정
        response.reset() ;
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Description", "JSP Generated Data");
 
        if(!skip){ 
            // IE
            if(client.indexOf("MSIE") != -1){
                response.setHeader ("Content-Disposition", "attachment; filename="+new String(orgfilename.getBytes("KSC5601"),"ISO8859_1"));
 
            }else{
                // 한글 파일명 처리
                orgfilename = new String(orgfilename.getBytes("utf-8"),"iso-8859-1");
 
                response.setHeader("Content-Disposition", "attachment; filename=\"" + orgfilename + "\"");
                response.setHeader("Content-Type", "application/octet-stream; charset=utf-8");
            } 
             
            response.setHeader ("Content-Length", ""+file.length() );
 
 
       
            os = response.getOutputStream();
            byte b[] = new byte[(int)file.length()];
            int leng = 0;
             
            while( (leng = in.read(b)) > 0 ){
                os.write(b,0,leng);
            }
 
        }else{
            response.setContentType("text/html;charset=UTF-8");
            out.println("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>");
 
        }
         
        in.close();
        os.close();
 
    }catch(Exception e){
      e.printStackTrace();
    }
%>


