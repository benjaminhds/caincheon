// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CommonException.java

package kr.caincheon.church.common.base;


public class CommonException extends Exception
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//
	private String errCode;
    private String errMessage;
    private Object data;
    private String lastSQL;
    
    public CommonException()
    {
    }

    public CommonException(String message) {
        super(message);
        errMessage = message;
    }

    public CommonException(String message, Throwable cause) {
        super(message, cause);
        errMessage = message;
    }

    public CommonException(String message, Throwable cause, String errCode) {
        super(message, cause);
        errMessage = message;
        this.errCode = errCode;
    }

    public CommonException(String message, Throwable cause, String errCode, Object data) {
        super(message, cause);
        errMessage = message;
        this.errCode = errCode;
        this.data = data;
    }

    public CommonException(String message, String errCode) {
        super(message);
        errMessage = message;
        this.errCode = errCode;
    }

    public CommonException(String message, String errCode, Object data) {
        super(message);
        errMessage = message;
        this.errCode = errCode;
        this.data = data;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public String getMsg() {
        return errMessage;
    }

    public void setMsg(String msg) {
        errMessage = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
    public CommonException setSql(String lastSQL) {
    	this.lastSQL = lastSQL;
    	return this;
    }
    
    public String getSql() {
    	return this.lastSQL;
    }
}
