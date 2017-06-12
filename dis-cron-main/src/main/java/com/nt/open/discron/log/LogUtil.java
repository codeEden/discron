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
	
	public static void info(String message){
		logger.info(message);
	}
	
	public static void info(String className,String message){
		logger.info(message);
	}
	
	public static void info(String message,Object... obj){
		logger.info(message, obj);
	}
}
