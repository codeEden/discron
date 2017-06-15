/**
 * 
 */
package com.nt.open.discron.dao;

import org.apache.ibatis.annotations.Param;

import com.nt.open.discron.entity.po.JobHisPO;

/**
 * @author bjfulianqiu
 *
 */
public interface JobHisDao {

	public void insert(JobHisPO jobHisDao);
	
	public JobHisPO get(@Param("id") Long id);
}
