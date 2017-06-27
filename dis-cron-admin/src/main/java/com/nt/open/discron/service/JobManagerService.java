/**
 * 
 */
package com.nt.open.discron.service;

import java.util.List;

import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.vo.JobPageResult;
import com.nt.open.discron.vo.RequestVO;

/**
 * @author bjfulianqiu
 *
 */
public interface JobManagerService {

	public JobPageResult list(RequestVO requestVO);
}
