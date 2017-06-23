/**
 * 
 */
package com.nt.open.discron.run;

import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.mybatis.ProxyUtil;

/**
 * @author bjfulianqiu
 * 检查是有些job因为宕机而任务没有机器执行
 */
public class ResetJob implements Runnable{

	@Override
	public void run() {
		JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
		jobDao.resetJob();
	}

}
