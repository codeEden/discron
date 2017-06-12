/**
 * 
 */
package com.nt.open.proc.util;

import java.util.Date;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

/**
 * @author bjfulianqiu
 *
 */
public class LogUtil {

	static Logger logger = Logger.getLogger(LogUtil.class);
	
	private static FileAppender fileAppender=null;
	
	public static void info(String message){
		try{
			if(fileAppender==null){
				
				PatternLayout patternLayout=new PatternLayout();
				patternLayout.setConversionPattern("%d %p [%c] - %m%n");
				String day=DateUtil.date2String(new Date());
				String file = String.format("%s/proc.%s_%s.log", AppContext.APPCONTEXT.getRootPath(),
						AppContext.APPCONTEXT.getJobName(),day);
				System.out.println("file==="+file);
				fileAppender=new FileAppender(patternLayout,file,true);
				fileAppender.setName("procAppender");
				
				logger.addAppender(fileAppender);
			}
			logger.info(message);
		}catch(Exception e){
			
		}
	}
}
