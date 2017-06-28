/**
 * 
 */
package com.nt.open.discron.vo;

import java.util.List;

import com.nt.open.discron.common.pager.PageCondition;
import com.nt.open.discron.entity.po.JobPO;

import lombok.Getter;
import lombok.Setter;

/**
 * @author bjfulianqiu
 *
 */
@Getter @Setter
public class JobPageResult {

	private PageCondition pageInfo;
	
	private List<JobVo> resultList;
}
