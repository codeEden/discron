/**
 * 
 */
package com.nt.open.discron.dao;

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
	
	public int registerHandleHost(JobPO jobPO);
}
