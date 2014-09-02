package com.xdnote.xdobx.logic;

import java.io.PrintStream;
import java.io.PrintWriter;
/**
 * <p>
 * 自定义异常封装，本系统中所有异常都封装为自定义的异常，包含一个错误码以便统一提示处理
 * </p>
 * @author xdnote.com
 * 
 * */
public class RunException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6873734143248693812L;
	
	private Exception e = null;
	
	private String error_code;
	
	private String[] param = new String[]{};

	public RunException(String code){
//		super();
		this.error_code = code;
	}
	public RunException(String code,String... param){
//		super();
		this.error_code = code;
		this.param=param;
	}

	public RunException(String code, Exception e){
//		super();
		this.error_code = code;
		this.e = e;
	}
	
	
	@Override
	public String getMessage() {
		if(e!=null)
		return e.getMessage();
		return this.toString();
	}

	@Override
	public String getLocalizedMessage() {
		if(e!=null)
		return e.getLocalizedMessage();
		return this.toString();
	}

	@Override
	public Throwable getCause() {
		if(e!=null)
		return e.getCause();
		return null;
	}

	@Override
	public synchronized Throwable initCause(Throwable cause) {
		if(e!=null)
		return e.initCause(cause);
		return null;
	}

	@Override
	public String toString() {
		return I18N.CODE(this.error_code,this.param);
	}

	@Override
	public void printStackTrace() {
		if(e!=null){
			e.printStackTrace();
		}
	}

	@Override
	public void printStackTrace(PrintStream s) {
		if(e!=null){
			e.printStackTrace(s);
		}else{
			s.println(this.toString());
		}
	}

	@Override
	public void printStackTrace(PrintWriter s) {
		if(e!=null){
			e.printStackTrace(s);
		}else{
			s.println(this.toString());
		}
		
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		if(e!=null)
		return e.fillInStackTrace();
		return null;
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		if(e!=null)
		return e.getStackTrace();
		return null;
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
	}
	
}
