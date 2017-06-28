/**
 * 
 */
package com.nt.open.discron.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.nt.open.discron.common.pager.PageCondition;
import com.nt.open.discron.common.util.BeanUtil;
import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.service.JobManagerService;
import com.nt.open.discron.vo.JobPageResult;
import com.nt.open.discron.vo.JobVo;
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
			jobPageResult.setResultList(po2vo(list));
		}
		PageCondition pageCondition=new PageCondition();
		pageCondition.setRowCount(count);
		pageCondition.setPageIndex(requestVO.getPageIndex());
		pageCondition.setPageSize(requestVO.getPageSize());
		jobPageResult.setPageInfo(pageCondition);
		
		return jobPageResult;
	}
	
	private List<JobVo> po2vo(List<JobPO> jobPOList){
		List<JobVo> result=Lists.newArrayList();
		if(!CollectionUtils.isEmpty(jobPOList)){
			for(JobPO jobPO:jobPOList){
				JobVo jobVo=new JobVo();
				BeanUtil.objectCopy(jobPO, jobVo);
				result.add(jobVo);
			}
		}
		return result;
	}

}
