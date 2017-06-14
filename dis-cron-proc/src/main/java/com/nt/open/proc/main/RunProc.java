/**
 * 
 */
package com.nt.open.proc.main;

import com.nt.open.proc.util.AppContext;
import com.nt.open.proc.util.LogUtil;

/**
 * @author bjfulianqiu
 *
 */
public class RunProc {
	
	private final static long CHECK_INTERVEL=200L;
	

	public static void main(String[] args) {
		try{
			String jobName=args[0];
			String rootPath=args[1];
			String url=args[2];
			String typeStr=args[3];
			String timeout=args[4];
			String nettyPort=args[5];
			
//			String jobName="discrontest1";
//			String rootPath="D:/workspace/open_source/dis-cron/dis-cron-main/target";
//			String url="com.test.Test";
//			String typeStr="2";
//			String timeout="2000";
//			String nettyPort="8192";
			
			AppContext.APPCONTEXT.setJobName(jobName);
			AppContext.APPCONTEXT.setNettyPort(Integer.parseInt(nettyPort));
			AppContext.APPCONTEXT.setRootPath(rootPath);
			
			long time=0L;
			
			Thread exeThread=new Thread(new ExeThread(jobName,Integer.parseInt(typeStr), url, rootPath));
			exeThread.start();
//			new ExeThread(jobName,Integer.parseInt(typeStr), url, rootPath).run();
			while(!AppContext.APPCONTEXT.isOver()){
				if(time>=Long.parseLong(timeout)){
					LogUtil.info("任务超时,exit proc timeout="+time);
					System.exit(0);
				}
				Thread.sleep(CHECK_INTERVEL);
				time+=CHECK_INTERVEL;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}
}
