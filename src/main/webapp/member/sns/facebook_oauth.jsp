<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.apache.http.impl.client.BasicResponseHandler
				, org.apache.http.impl.client.DefaultHttpClient
				, org.apache.http.client.methods.HttpGet
				, java.util.* "%><%!

	public boolean isNotEmpty(Object o) {
		return o==null ? false : true;
	}
	
	public String pnull(Object o) {
		return o==null ? "":String.valueOf(o);
	}
	
	public String pnull(HttpServletRequest request, String key) {
		Object o = request.getParameter(key);
		return o==null ? "":String.valueOf(o);
	}
	
	public String pnullSession(HttpServletRequest request, String key, String defaultVal) {
		HttpSession session = request.getSession(true);
		if(session==null) {
			Object o = request.getAttribute(key);
			return o==null ? defaultVal : String.valueOf(o);
		}
		Object o = session.getAttribute(key);
		return o==null ? defaultVal : String.valueOf(o);
	}
	
	%><%
  // response set
  response.setContentType("application/json");

  // response data
  Map rtMap = new HashMap();
  

  // facebook OAuth items
  String facebook_appid     = "1884752561738506";  // app id
  String facebook_appkey    = "49e43bdd66641c2d2c2482daf0d8024c";  // app secret code
  
  String code             = request.getParameter("code");
  String errorReason      = request.getParameter("error_reason");
  String error            = request.getParameter("error");
  String errorDescription = request.getParameter("error_description");
  
  String accesstoken  = "";
  String result       = "", status="success";

  if( isNotEmpty(code) ) {
      HttpGet get = new HttpGet("https://graph.facebook.com/oauth/access_token" +
              "?client_id="+facebook_appid +
              "&client_secret="+facebook_appkey +
              "&redirect_uri=http://www.caincheon.or.kr/member/sns/facebook_oauth.jsp" +
              "&code=" + code );
      
      DefaultHttpClient http = new DefaultHttpClient();
      result = http.execute(get, new BasicResponseHandler());
      
      accesstoken = result.substring(result.indexOf("=")+1);
      status="fail";
  }
  
  rtMap.put("token", accesstoken);
  rtMap.put("status", status);
  
  // send a response data
  out.println(rtMap);
%>