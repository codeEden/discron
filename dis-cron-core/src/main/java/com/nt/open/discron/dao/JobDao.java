/**
 * 
 */
package com.nt.open.discron.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.nt.open.discron.entity.po.JobPO;

/**
 * @author bjfulianqiu
 *
 */
public interface JobDao {

	public Long insert(JobPO jobPO);
	
	public JobPO get(@Param("id") Long id);
	
	public List<JobPO> getList(JobPO jobPO);
	
	public List<JobPO> getListByPage(@Param("start") Integer start,@Param("pageSize") Integer pageSize);
	
	public Integer count();
	
	public int registerHandleHost(JobPO jobPO);
	
	public List<JobPO> getListByIds(@Param("ids")List<Long> ids);
	
	public int update(@Param("id") Long id,@Param("lastExeTime") Date lastExeTime,@Param("updateTime") Date updateTime);
	
	public int resetJob();
}
