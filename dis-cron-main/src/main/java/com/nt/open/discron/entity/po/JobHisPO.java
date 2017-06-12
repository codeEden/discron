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
	private Long jobName;
	private String cron;
	/**
	 * 超时时间  秒
	 */
	private Integer timeout;
	/**
	 * type(1.http 2.class)
	 */
	private String type;
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
	private int executeTimes;
	
}
