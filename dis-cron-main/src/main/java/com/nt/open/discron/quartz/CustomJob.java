/**
 * 
 */
package com.nt.open.discron.quartz;

import java.io.File;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.util.AppContext;
import com.nt.open.discron.util.DateUtil;

/**
 * @author bjfulianqiu
 *
 */
public class CustomJob implements Job {
	
	private static Logger logger = LoggerFactory.getLogger("discron");

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
        	String paramStr=this.getParamStr(jobName, rootPath, url, type, timeout, id,  AppContext.APPCONTEXT.NETTY_SERVER_PORT,now);
        	String cmd = String.format("java -jar %s %s",
        			jarpathString,
        			paramStr
        			);
        	logger.info("cmd={}",cmd);
        	// 记录启动时间
        	JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
        	jobDao.update(id, now, now);
        	
			Process process = Runtime.getRuntime().exec(cmd);
			AppContext.APPCONTEXT.addJobProcMap(jobName,process);
			logger.info("子进程启动成功！");
		} catch (Exception e) {
			logger.error("启动进程错误", e);
		}
        
        
	    /*
	    logger.info("rootPath={}",rootPath);
		if(type==JobEnum.Type.HTTP.getCode()){
			logger.info("job执行,key={},执行方式={},url={}",context.getJobDetail().getKey().getName(),JobEnum.Type.HTTP.getMessage(),url);
		}else if(type==JobEnum.Type.CLASS.getCode()){
			logger.info("job执行,key={},执行方式={},url={}",context.getJobDetail().getKey().getName(),JobEnum.Type.CLASS.getMessage(),url);
		}*/
		
	}
	
	private String getParamStr(String jobName, String rootPath, String url, Integer type, Integer timeout, Long id,
			Integer port,Date now) throws ParseException {
		Map<String,Object> paramMap=Maps.newHashMap();
		paramMap.put("jobName", jobName);
		paramMap.put("rootPath", rootPath);
		paramMap.put("url", url);
		paramMap.put("type", type);
		paramMap.put("id", id);
		paramMap.put("port", port);
		paramMap.put("startTime", DateUtil.date2String(now));
		
		return JSON.toJSONString(paramMap);
	}

}
