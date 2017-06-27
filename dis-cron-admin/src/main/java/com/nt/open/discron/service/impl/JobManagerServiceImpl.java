/**
 * 
 */
package com.nt.open.discron.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.nt.open.discron.common.pager.PageCondition;
import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.service.JobManagerService;
import com.nt.open.discron.vo.JobPageResult;
import com.nt.open.discron.vo.RequestVO;

/**
 * @author bjfulianqiu
 *
 */
@Service("jobManagerService")
public class JobManagerServiceImpl implements JobManagerService {

	@Override
	public JobPageResult list(RequestVO requestVO) {
		JobPageResult jobPageResult=new JobPageResult();
		JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
		Integer count=jobDao.getCount();
		if(count>0){
			List<JobPO> list=jobDao.getListByPage(requestVO.getStart(), requestVO.getPageSize());
			jobPageResult.setResultList(list);
		}
		PageCondition pageCondition=new PageCondition();
		pageCondition.setRowCount(count);
		pageCondition.setPageIndex(requestVO.getPageIndex());
		pageCondition.setPageSize(requestVO.getPageSize());
		jobPageResult.setPageInfo(pageCondition);
		
		return jobPageResult;
	}

}
