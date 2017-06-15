/**
 * 
 */
package com.nt.open.discron.entity.po;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bjfulianqiu
 *
 */
@Setter @Getter
public class JobHisPO {

	private Long id;
	private Long jobId;
	private String jobName;
	private String cron;
	/**
	 * 超时时间  秒
	 */
	private Integer timeout;
	/**
	 * type(1.http 2.class)
	 */
	private Integer type;
	/**
	 * http或者类地址
	 */
	private String url;
	/**
	 * 最后执行时间
	 */
	private Date lastExeTime;
	/**
	 * 执行时常 （s秒）
	 */
	private Long executeTimes;
	/**
	 * 执行job机器
	 */
	private String handleHost;
	
	private String errorMsg;
	
	private Date createTime;
	
	public JobHisPO(JobPO jobPO){
		this.jobId=jobPO.getId();
		this.jobName=jobPO.getJobName();
		this.cron=jobPO.getCron();
		this.timeout=jobPO.getTimeout();
		this.type=jobPO.getType();
		this.url=jobPO.getUrl();
		this.lastExeTime=jobPO.getLastExeTime();
		this.handleHost=jobPO.getHandleHost();
	}
	
}
