/**
 * 
 */
package com.nt.open.proc.main;

import java.util.Map;

import com.alibaba.fastjson.JSON;
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
			String param=args[0];
			Map<String,Object> paramMap=JSON.parseObject(param);
			String jobName=(String) paramMap.get("jobName");
			String nettyPort=(String) paramMap.get("nettyPort");
			String rootPath=(String) paramMap.get("rootPath");
			String typeStr=(String) paramMap.get("typeStr");
			String url=(String) paramMap.get("url");
			String id=(String) paramMap.get("id");
			String timeout=(String) paramMap.get("timeout");
			String startTime=(String) paramMap.get("startTime");
			
//			paramMap.put("jobName", jobName);
//			paramMap.put("rootPath", rootPath);
//			paramMap.put("url", url);
//			paramMap.put("type", type);
//			paramMap.put("id", id);
//			paramMap.put("port", port);
			
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
			
			Thread exeThread=new Thread(new ExeThread(jobName,Integer.parseInt(typeStr), url, rootPath,id,startTime));
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
