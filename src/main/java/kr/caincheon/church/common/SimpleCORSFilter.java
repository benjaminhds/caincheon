package kr.caincheon.church.common;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class SimpleCORSFilter
    implements Filter
{

	java.text.SimpleDateFormat sdf2 = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	
    public void init(FilterConfig filterconfig) throws ServletException { }
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
    		throws IOException, ServletException
    {
    	/*
    	 * CORS ( Sandbox의 문제를 해결하고 다른 도메인의 요청처리를 위한 헤더 설정 )
    	 */
        HttpServletResponse res = (HttpServletResponse)response;
        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        res.setHeader("Access-Control-Max-Age", "3600");
        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        res.setHeader("Access-Control-Allow-Origin", "*");
        try {
			chain.doFilter(request, response);
		} catch (Exception e) {
			
			String err_msg = "SimpleCORSFilter.doFilter()["+sdf2.format(new Date())+"] ERROR: " + e.getMessage();
			String file_download_jsp = ".inc.file_005fdownload_jsp", stacks = "";
			boolean isFound = false;
			
			StackTraceElement[] ste = e.getStackTrace();//Thread.currentThread().getStackTrace();
			for(int i=0, i2=ste.length; i<i2; i++) {
				stacks = ( "\t >> "+ ste[i].toString() );
				if(ste[i].toString().indexOf(file_download_jsp) > 0) {
					isFound = true;
					break;
				}
			}
			
		    
			err_msg += ste[1].getClassName()+"."+ste[1].getMethodName()+"() >>> ";
		    err_msg += "\n                           You need to find a source code, which is like a `response.getOutputStream()`. ";
		    if( !isFound ) {
				System.err.println( err_msg );
				System.out.println( stacks );
		    }
		    
		 
		    // go to the error page with error contents
		    request.setAttribute("ERR_MSG", err_msg);
		    request.setAttribute("STACKS", stacks);
		    
		    RequestDispatcher dispatcher = request.getRequestDispatcher("error.jsp");
	        dispatcher.forward(request, response);
		}
    }

    
    public void destroy() {}
}
