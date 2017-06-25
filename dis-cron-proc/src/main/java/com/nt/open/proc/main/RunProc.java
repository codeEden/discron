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
	
	private final static String ENCODE="UTF-8";
	
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
		try{
			String param=URLDecoder.decode(args[0],ENCODE);
			Map<String,Object> paramMap=parseParam(param);
			String jobName=(String) paramMap.get("jobName");
			String nettyPort=(String) paramMap.get("nettyPort");
			String rootPath=(String) paramMap.get("rootPath");
			String typeStr=(String) paramMap.get("type");
			String url=(String) paramMap.get("url");
			String id=(String) paramMap.get("id");
			String timeoutStr=(String) paramMap.get("timeout");
			String startTime=(String) paramMap.get("startTime");
			String procId=(String) paramMap.get("procId");
			String jobParam=null;
			if(paramMap.get("param")!=null){
				jobParam=URLDecoder.decode((String)paramMap.get("param"), ENCODE);
			}
			
			
			AppContext.APPCONTEXT.setJobName(jobName);
			AppContext.APPCONTEXT.setNettyPort(Integer.parseInt(nettyPort));
			AppContext.APPCONTEXT.setRootPath(rootPath);
			
			LogUtil.info("paramMap=="+paramMap.toString());
			
			
//			Thread exeThread=new Thread(new ExeThread(jobName,Integer.parseInt(typeStr), url, rootPath,id,startTime));
//			exeThread.start();
			ExeThread exeThread=new ExeThread();
			exeThread.setId(id);
			exeThread.setJarPath(rootPath);
			exeThread.setJobName(jobName);
			exeThread.setJobParam(jobParam);
			exeThread.setProcId(procId);
			exeThread.setStartTime(startTime);
			exeThread.setType(Integer.parseInt(typeStr));
			exeThread.setUrl(url);
			exeThread.run();
			
			Long timeout=null;
			if(!Strings.isNullOrEmpty(timeoutStr)){
				timeout=Long.parseLong(timeoutStr);
				//预留10s的buffer
				timeout=timeout+10*1000;
			}
			while(!AppContext.APPCONTEXT.isOver()){
				if(timeout!=null){
					if(timeout.longValue()<0){
						break;
					}else{
						timeout=timeout-CHECK_INTERVEL;
					}
				}
				Thread.sleep(CHECK_INTERVEL);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			LogUtil.error("进程启动错误", e);
		}
		System.exit(0);
	}
}
