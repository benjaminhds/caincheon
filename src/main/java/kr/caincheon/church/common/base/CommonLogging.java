package kr.caincheon.church.common.base;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 어느 프로그램에서 어느 함수가 어느 라인에서 로깅을 하는지를 트랙킹해서 로깅을 한다.
 * @author benjamin
 * @since 2017.09.xx
 */
public class CommonLogging {
	
    private SimpleDateFormat sdf9 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    
	//
	public void D(Thread currentThread, String msg) {
		__D(currentThread.getStackTrace(), msg, null);
	}
	public void D(Thread currentThread, String msg, Exception e) {
		__D(currentThread.getStackTrace(), msg, e);
	}
	public void D(StackTraceElement[] ste, String msg) {
		__D(ste, msg, null);
	}
	public void __D(StackTraceElement[] ste, String msg, Exception e) {
		String sss = "["+sdf9.format(new Date())+"] DEBUG: "+ste[1].getClassName()+"."+ste[1].getMethodName()+"(), "+ste[1].getLineNumber()+" >>> " + msg;
		System.out.println( sss );
		if(e!=null)
			e.printStackTrace();
	}
	
	//
	public void I(Thread currentThread, String msg) {
		__I(currentThread.getStackTrace(), msg, null);
	}
	public void I(Thread currentThread, String msg, Exception e) {
		__I(currentThread.getStackTrace(), msg, e);
	}
	public void I(StackTraceElement[] ste, String msg) {
		__I(ste, msg, null);
	}
	public void __I(StackTraceElement[] ste, String msg, Exception e) {
		String sss = "["+sdf9.format(new Date())+"] INFO : "+ste[1].getClassName()+"."+ste[1].getMethodName()+"(), "+ste[1].getLineNumber()+" >>> " + msg;
		System.out.println( sss );
		if(e!=null)
			e.printStackTrace();
	}

	//
	public void E(Thread currentThread, String msg) {
		__E(currentThread.getStackTrace(), msg, null);
	}
	public void E(Thread currentThread, String msg, Exception e) {
		__E(currentThread.getStackTrace(), msg, e);
	}
	public void E(StackTraceElement[] ste, String msg) {
		__E(ste, msg, null);
	}
	public void __E(StackTraceElement[] ste, String msg, Exception e) {
		String sss = "["+sdf9.format(new Date())+"] ERROR: "+ste[1].getClassName()+"."+ste[1].getMethodName()+"(), "+ste[1].getLineNumber()+" >>> " + msg;
		System.err.println( sss );
		if(e!=null)
			e.printStackTrace();
	}
	

	//
	public void D(Logger _logger, String msg) {
		_D(_logger, null, msg, null);
	}
	public void D(Logger _logger, Thread currentThread, String msg) {
		_D(_logger, currentThread.getStackTrace(), msg, null);
	}
	public void D(Logger _logger, Thread currentThread, String msg, Exception e) {
		_D(_logger, currentThread.getStackTrace(), msg, e);
	}
	public void D(Logger _logger, StackTraceElement[] ste, String msg) {
		_D(_logger, ste, msg, null);
	}
	public void _D(Logger _logger, StackTraceElement[] ste, String msg, Exception e) {
		if(_logger != null && ste==null) 
			_logger.debug("[] >>> " + msg);
		else if(_logger != null) 
			_logger.debug("["+ste[1].getMethodName()+"(), "+ste[1].getLineNumber()+"] >>> " + msg);
		else 
			D(ste, msg);
		if(e!=null)
			e.printStackTrace();
	}
	
	//
	public void I(Logger _logger, Thread currentThread, String msg) {
		_I(_logger, currentThread.getStackTrace(), msg, null);
	}
	public void I(Logger _logger, Thread currentThread, String msg, Exception e) {
		_I(_logger, currentThread.getStackTrace(), msg, e);
	}
	public void I(Logger _logger, StackTraceElement[] ste, String msg) {
		_I(_logger, ste, msg, null);
	}
	public void _I(Logger _logger, StackTraceElement[] ste, String msg, Exception e) {
		if(_logger != null && ste==null) 
			_logger.info("[] >>> " + msg);
		else if(_logger != null) 
			_logger.info("["+ste[1].getMethodName()+"(), "+ste[1].getLineNumber()+"] >>> " + msg);
		else 
			I(ste, msg);
		if(e!=null)
			e.printStackTrace();
	}

	//
	public void E(Logger _logger, Thread currentThread, String msg) {
		_E(_logger, currentThread.getStackTrace(), msg, null);
	}
	public void E(Logger _logger, Thread currentThread, String msg, Exception e) {
		_E(_logger, currentThread.getStackTrace(), msg, e);
	}
	public void E(Logger _logger, StackTraceElement[] ste, String msg) {
		_E(_logger, ste, msg, null);
	}
	public void _E(Logger _logger, StackTraceElement[] ste, String msg, Exception e) {
		if(_logger != null && ste==null) 
			_logger.error("[] >>> " + msg);
		else if(_logger != null) 
			_logger.error("["+ste[1].getMethodName()+"(), "+ste[1].getLineNumber()+"] >>> " + msg);
		else 
			E(ste, msg);
		if(e!=null)
			e.printStackTrace();
	}
	
}
