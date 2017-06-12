/**
 * 
 */
package com.nt.open.discron.dao;

import java.util.List;

import com.nt.open.discron.entity.po.JobPO;

/**
 * @author bjfulianqiu
 *
 */
public interface JobDao {

	public Long insert(JobPO jobPO);
	
	public List<JobPO> getList(JobPO jobPO);
	
	public int registerHandleHost(JobPO jobPO);
}
