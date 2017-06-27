/**
 * 
 */
package com.nt.open.discron.service;

import java.util.List;

import com.nt.open.discron.entity.po.JobPO;

/**
 * @author bjfulianqiu
 *
 */
public interface JobManagerService {

	public List<JobPO> list(Long start,Integer pageSize);
}
