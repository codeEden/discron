/**
 * 
 */
package com.nt.open.discron.quartz;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nt.open.discron.util.AppContext;

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
		// TODO Auto-generated method stub
		Integer type=context.getJobDetail().getJobDataMap().getInt("type");
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
	    String jarpathString = rootPath + "/dis-cron-proc.jar";
        String cmd = String.format("java -jar %s %s %s %s %d %d %d",
            jarpathString,
            jobName,
            rootPath,
            url,
            type,
            timeout,
            AppContext.APPCONTEXT.NETTY_SERVER_PORT
        );
        logger.info("cmd={}",cmd);
        try {
			Process process = Runtime.getRuntime().exec(cmd);
			AppContext.APPCONTEXT.addJobProcMap(jobName,process);
			logger.info("子进程启动成功！");
		} catch (IOException e) {
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

}
