/**
 * 
 */
package com.nt.open.discron.service.impl;

import java.util.List;

import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.service.JobManagerService;

/**
 * @author bjfulianqiu
 *
 */
public class JobManagerServiceImpl implements JobManagerService {

	@Override
	public List<JobPO> list(Long start, Integer pageSize) {
		JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
		return null;
	}

}
