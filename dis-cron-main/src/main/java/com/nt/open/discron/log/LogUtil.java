package com.nt.open.discron.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author bjfulianqiu
 *
 */
public class LogUtil {
	private static Logger logger = null;
	
	static{
//		DOMConfigurator.configure("log4j.xml");
		logger = LoggerFactory.getLogger("discron");
	}
	
//	private static String getInvokeMethod(){
//		//获取调用者的类名
//		String classname = new Exception().getStackTrace()[2].getClassName(); 
//		//获取调用者的方法名
//		String method_name = new Exception().getStackTrace()[2].getMethodName();
//		
//		return StringUtil.joinStr("[ ",classname,"_",method_name," ] ");
//		return "";
//	}
	
	public static void info(String message){
		logger.info(message);
	}
	
	public static void error(String message,Throwable e){
		logger.error(message,e);
	}
	
//	public static void info(String className,String message){
//		logger.info(message);
//	}
	
	public static void info(String message,Object... obj){
		logger.info(message, obj);
	}
}
