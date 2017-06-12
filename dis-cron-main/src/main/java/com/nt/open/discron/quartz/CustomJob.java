/**
 * 
 */
package com.nt.open.discron.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nt.open.discron.entity.emun.JobEnum;

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
		if(type==JobEnum.Type.HTTP.getCode()){
			logger.info("job执行,key={},执行方式=",context.getJobDetail().getKey(),JobEnum.Type.HTTP.getMessage());
		}else if(type==JobEnum.Type.CLASS.getCode()){
			logger.info("job执行,key={},执行方式=",context.getJobDetail().getKey(),JobEnum.Type.CLASS.getMessage());
		}
		
	}

}
