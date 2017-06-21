/**
 * 
 */
package com.nt.open.discron.quartz;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.google.common.collect.Maps;
import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.entity.ProcInfo;
import com.nt.open.discron.log.LogUtil;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.util.AppContext;
import com.nt.open.discron.util.DateUtil;

/**
 * @author bjfulianqiu
 *
 */
@DisallowConcurrentExecution
public class CustomJob implements Job {
	//StatefulJob

	/* (non-Javadoc)
	 * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
	 */
	public void execute(JobExecutionContext context) throws JobExecutionException {
		Integer type=context.getJobDetail().getJobDataMap().getInt("type");
		Long id=context.getJobDetail().getJobDataMap().getLong("id");
		String url=context.getJobDetail().getJobDataMap().getString("url");
		Integer timeout=context.getJobDetail().getJobDataMap().getInt("timeout");
		String jobName=context.getJobDetail().getJobDataMap().getString("jobName");
		String rootPath = "";
		try {
			File file = new File(this.getClass().getResource("/").toURI());
			rootPath = file.getParent();
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
		}
        try {
        	Date now=new Date();
        	String jarpathString = rootPath + "/dis-cron-proc.jar";
        	Map<String,Object> paramMap=Maps.newHashMap();
        	paramMap.put("jobName", jobName);
        	paramMap.put("rootPath", rootPath);
        	paramMap.put("url", url);
        	paramMap.put("type", type);
        	paramMap.put("timeout", timeout);
        	paramMap.put("id", id);
        	paramMap.put("nettyPort", AppContext.APPCONTEXT.NETTY_SERVER_PORT);
        	paramMap.put("startTime", DateUtil.date2String(now));
        	
        	String paramStr=this.getParamStr(paramMap);
        	String cmd = String.format("java -jar %s %s",
        			jarpathString,
        			paramStr
        			);
        	LogUtil.info("cmd={}",cmd);
        	// 记录启动时间
        	JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
        	jobDao.update(id, now, now);
        	
			Process process = Runtime.getRuntime().exec(cmd);
			
			
			ProcInfo procInfo=new ProcInfo();
			procInfo.setJobId(id);
			procInfo.setProcess(process);
			procInfo.setStartTime(now.getTime());
			procInfo.setTimeout(timeout);
			
			AppContext.APPCONTEXT.addJobProcList(procInfo);
			LogUtil.info("子进程启动成功！");
		} catch (Exception e) {
			LogUtil.error("启动进程错误", e);
		}
        
        
	    /*
	    logger.info("rootPath={}",rootPath);
		if(type==JobEnum.Type.HTTP.getCode()){
			logger.info("job执行,key={},执行方式={},url={}",context.getJobDetail().getKey().getName(),JobEnum.Type.HTTP.getMessage(),url);
		}else if(type==JobEnum.Type.CLASS.getCode()){
			logger.info("job执行,key={},执行方式={},url={}",context.getJobDetail().getKey().getName(),JobEnum.Type.CLASS.getMessage(),url);
		}*/
		
	}
	
	private String getParamStr(Map<String,Object> paramMap) throws ParseException, UnsupportedEncodingException {
		StringBuilder result=new StringBuilder("");
		for(Entry<String, Object> entry:paramMap.entrySet()){
			if(!result.toString().equals("")){
				result.append("&");
			}
			result.append(entry.getKey());
			result.append("=");
			result.append(String.valueOf(entry.getValue()));
		}
		return URLEncoder.encode(result.toString(),"UTF-8");
	}

}
