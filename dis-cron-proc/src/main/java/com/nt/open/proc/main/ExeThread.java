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

import com.google.common.collect.Lists;
import com.nt.open.proc.entity.emun.JobEnum;
import com.nt.open.proc.util.AppContext;
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
	private URLClassLoader urlClassLoader;

	

	public ExeThread(String jobName,Integer type, String url, String jarPath) {
		super();
		this.jobName=jobName;
		this.type = type;
		this.url = url;
		this.jarPath = jarPath;
	}

	public void run() {
		try{
			LogUtil.info("runProc start....jobName="+jobName);
			
			if(type==JobEnum.Type.HTTP.getCode()){
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
			LogUtil.error("任务执行错误",e);
		}finally{
			AppContext.APPCONTEXT.setOver(true);
		}
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
