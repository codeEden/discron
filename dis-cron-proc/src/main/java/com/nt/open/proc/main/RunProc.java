/**
 * 
 */
package com.nt.open.proc.main;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.nt.open.proc.util.AppContext;
import com.nt.open.proc.util.LogUtil;

/**
 * @author bjfulianqiu
 *
 */
public class RunProc {
	
	private final static long CHECK_INTERVEL=200L;
	
	private static Map<String,Object> parseParam(String paramStr){
		Map<String,Object> paramMap=Maps.newHashMap();
		if(!Strings.isNullOrEmpty(paramStr)){
			String[] params=paramStr.split("&");
			for(String param:params){
				String[] paramArr=param.split("=");
				paramMap.put(paramArr[0], paramArr[1]);
			}
		}
		
		return paramMap;
	}
	

	public static void main(String[] args) throws UnsupportedEncodingException {
		AppContext.APPCONTEXT.setRootPath("F:\\opensource\\discron\\dis-cron-main\\target");
		LogUtil.info("args[0]="+URLDecoder.decode(args[0].toString(), "UTF-8"));
		
		
		try{
			String param=URLDecoder.decode(args[0],"UTF-8");
			LogUtil.info("param=="+param);
			Map<String,Object> paramMap=parseParam(param);
			LogUtil.info("paramMap="+paramMap);
			String jobName=(String) paramMap.get("jobName");
			String nettyPort=(String) paramMap.get("nettyPort");
			String rootPath=(String) paramMap.get("rootPath");
			String typeStr=(String) paramMap.get("type");
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
			
			LogUtil.info("paramMap=="+paramMap.toString());
			
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
			LogUtil.error("进程启动错误", e);
		}
		System.exit(0);
	}
}
