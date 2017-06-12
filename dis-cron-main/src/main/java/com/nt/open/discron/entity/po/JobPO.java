/**
 * 
 */
package com.nt.open.discron.entity.po;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author bjfulianqiu
 *
 */
@Getter @Setter @ToString
public class JobPO {

	private Long id;
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
	 * 任务所属项目
	 */
	private String category;
	/**
	 * 最后执行时间
	 */
	private Date lastExeTime;
	/**
	 * 下次执行时间
	 */
	private Date nextExeTime;
	/**
	 * (0启用、1禁止)
	 */
	private Integer status;
	/**
	 * 执行job机器
	 */
	private String handleHost;
	
	private Date updateTime;
	
	private Date createTime;
}
