/**
 * 
 */
package com.nt.open.discron.quartz;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.GroupMatcher;

import com.google.common.collect.Lists;
import com.nt.open.discron.log.LogUtil;

/**
 * @author bjfulianqiu
 *
 */
public enum JobFactory {
	

	JOBFACTORY;
	
	// 初始化一个Schedule工厂
	SchedulerFactory schedulerFactory = null;
	// 通过schedule工厂类获得一个Scheduler类
	Scheduler scheduler = null;

	{
		schedulerFactory = new StdSchedulerFactory();
		try {
			scheduler = schedulerFactory.getScheduler();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isExistJob(String jobName){
		JobKey jobKey=new JobKey(jobName);
		try {
			return scheduler.checkExists(jobKey);
		} catch (SchedulerException e) {
			LogUtil.error("检查job是否存在错误", e);
			e.printStackTrace();
		}
		return true;
	}
	
	public void addJob(String jobName, String triggerName, String cron,Map<String,Object> dataMap) {
		try {
			JobKey jobKey = new JobKey(jobName);
			JobDataMap jobDataMap=new JobDataMap(dataMap);
			// 通过设置job name, job group, and executable job class初始化一个JobDetail
			JobDetail jobDetail = JobBuilder.newJob(CustomJob.class).setJobData(jobDataMap)
					.withIdentity(jobKey).build();
			// 设置触发器名称和触发器所属的组名初始化一个触发器
			CronTrigger trigger = null;
			trigger = TriggerBuilder.newTrigger().forJob(jobDetail)
					.withIdentity(triggerName)
					.withSchedule(CronScheduleBuilder.cronSchedule(cron))
					.build();

			scheduler.scheduleJob(jobDetail, trigger);
			scheduler.start();
			
			LogUtil.info("job添加到quartz成功，jobName={},cron={}",jobName,cron);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteJob(String jobName){
		JobKey jobKey = new JobKey(jobName);
		try {
			scheduler.deleteJob(jobKey);
		} catch (SchedulerException e) {
			e.printStackTrace();
			LogUtil.error("scheduler删除job错误", e);
		}
	}
	
	public List<Long> getAllJobKey(){
		List<Long> keyList=Lists.newArrayList();
		try {
			List<String> groupNames=scheduler.getJobGroupNames();
			for(String groupName:groupNames){
				GroupMatcher<JobKey> groupMatcher=GroupMatcher.groupEquals(groupName);
				Set<JobKey> jobKeySet=scheduler.getJobKeys(groupMatcher);
				for(JobKey jobKey:jobKeySet){
					String jobName=jobKey.getName();
					keyList.add(Long.parseLong(jobName));
				}
			}
		} catch (SchedulerException e) {;
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return keyList;
	}

}
