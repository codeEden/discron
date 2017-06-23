/**
 * 
 */
package com.nt.open.proc.main;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.nt.open.proc.entity.emun.JobEnum;
import com.nt.open.proc.netty.NettyClient;
import com.nt.open.proc.util.HTTPUtils;
import com.nt.open.proc.util.LogUtil;

/**
 * @author bjfulianqiu
 *
 */
public class ExeThread implements Runnable{
	
	private String jobName;
	private Integer type;
	private String url;
	private String jarPath;
	private String id;
	private String startTime;
	private String procId;
	private URLClassLoader urlClassLoader;

	

	public ExeThread(String jobName,Integer type, String url, String jarPath,String id,String startTime,String procId) {
		super();
		this.jobName=jobName;
		this.type = type;
		this.url = url;
		this.jarPath = jarPath;
		this.id=id;
		this.startTime=startTime;
		this.procId=procId;
	}

	public void run() {
		long start=System.currentTimeMillis();
		String errorMsg="";
		try{
			LogUtil.info("runProc start....jobName="+jobName);
			
			if(type==JobEnum.Type.HTTP.getCode()){
				Thread.sleep(30*1000);
				HTTPUtils.sendHttpOrHttps(url, "GET", null, null);
			}else if(type==JobEnum.Type.CLASS.getCode()){
				urlClassLoader = new URLClassLoader(getUrls(jarPath), Thread.currentThread().getContextClassLoader());
				Class<?> exeClass=urlClassLoader.loadClass(url);
				Method method=exeClass.getMethod("execute", String.class);
				Object objInstance=exeClass.newInstance();
				Object resultObj=method.invoke(objInstance, "proc_Test");
				LogUtil.info("runResult="+resultObj);
			}
			LogUtil.info("runProc end....jobName="+jobName);
		}catch(Exception e){
			errorMsg=getStatcTrace(e.getMessage(),e);
			LogUtil.error("任务执行错误",e);
		}finally{
			Map<String,Object> map=Maps.newHashMap();
			map.put("jobName", jobName);
			map.put("id", id);
			map.put("startTime", startTime);
			long end=System.currentTimeMillis();
			long time=end-start;
			map.put("runTime", time);
			map.put("errorMsg", errorMsg);
			map.put("procId", procId);
			NettyClient.sendMessage(JSON.toJSONString(map));
		}
	}
	
	private String getStatcTrace(String message,Exception e){
		StringBuilder sb=new StringBuilder(message);
		for(StackTraceElement element:e.getStackTrace()){
			sb.append(element.toString());
		}
		return sb.toString();
	}
	
	private URL[] getUrls(String jarpath) throws MalformedURLException{
		File fileDir=new File(jarpath);
		File[] files=fileDir.listFiles();
		List<URL> urlList=Lists.newArrayList();
		
		for(int i=0;i<files.length;i++){
			if(files[i].getName().endsWith(".jar")){
				urlList.add(new URL("file:"+ files[i].getPath()));
			}
		}
		URL[] result=urlList.toArray(new URL[urlList.size()]);
		return result;
	}

}
