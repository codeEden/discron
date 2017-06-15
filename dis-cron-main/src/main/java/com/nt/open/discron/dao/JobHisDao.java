/**
 * 
 */
package com.nt.open.discron.dao;

import org.apache.ibatis.annotations.Param;

/**
 * @author bjfulianqiu
 *
 */
public interface JobHisDao {

	public void insert(JobHisDao jobHisDao);
	
	public JobHisDao get(@Param("id") Long id);
}
