/**
 * 
 */
package com.nt.open.proc.main;

import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nt.open.proc.util.AppContext;
import com.nt.open.proc.util.LogUtil;

/**
 * @author bjfulianqiu
 *
 */
public class RunProc {
	

	public static void main(String[] args) {
		try{
			/*URL url=Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
			DOMConfigurator.configure(url);*/
			
			AppContext.APPCONTEXT.setJobName(args[0]);
			AppContext.APPCONTEXT.setNettyPort(Integer.parseInt(args[5]));
			AppContext.APPCONTEXT.setRootPath(args[1]);
			LogUtil.info("runProc  start.....");
			for(String arg:args){
				LogUtil.info("arg==="+arg);
				System.out.println("arg==="+arg);
			}
			System.out.println("runProc  start.....");
			LogUtil.info("runProc  end.....");
			System.out.println("runProc  end.....");
		}catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}
}
