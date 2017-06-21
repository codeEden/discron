/**
 * 
 */
package com.nt.open.discron.run;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.google.common.collect.Maps;
import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.entity.emun.JobEnum;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.log.LogUtil;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.quartz.JobFactory;
import com.nt.open.discron.util.HostHelper;

/**
 * @author bjfulianqiu
 *
 */
public class JobManager {
	
	private final long RUN_INTERVAL=1*1000L;
	
	public void run(){
		while(true){
			try {
				List<JobPO> jobList=getJobList();
//				logger.info("jobList = {}",jobList);
				
				if(!CollectionUtils.isEmpty(jobList)){
					for(JobPO jobPO:jobList){
						String jobName=jobPO.getJobName();
						String cron=jobPO.getCron();
						if(JobFactory.JOBFACTORY.isExistJob(jobName)){
//							logger.info("job已经存在于quartz,jobName={}",jobName);
							continue;
						}else{
							Map<String,Object> jobDataMap=Maps.newHashMap();
							jobDataMap.put("jobName", jobName);
							jobDataMap.put("id", jobPO.getId());
							jobDataMap.put("type", jobPO.getType());
							jobDataMap.put("url", jobPO.getUrl());
							jobDataMap.put("timeout", jobPO.getTimeout());
							JobFactory.JOBFACTORY.addJob(String.valueOf(jobPO.getId()),String.valueOf(jobPO.getId()), cron,jobDataMap);
						}
					}
				}
				
				Thread.sleep(RUN_INTERVAL);
			} catch (InterruptedException e) {
				LogUtil.error("jobManager错误", e);
				e.printStackTrace();
			}
		}
	}
	
	public List<JobPO> getJobList(){
		List<JobPO> jobList=new ArrayList<JobPO>();
		
		//获得代理类
		JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
		JobPO jobPO=new JobPO();
		jobPO.setHandleHost(HostHelper.getHostName());
		//争抢任务
		jobDao.registerHandleHost(jobPO);
		//查询主机对应的job
		jobPO.setStatus(JobEnum.Status.NORMAL.getCode());
		jobList=jobDao.getList(jobPO);
		
		return jobList;
	}
}
