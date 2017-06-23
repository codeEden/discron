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
	
	private static final long INTERVAL=5*1000L;

	@Override
	public void run() {
		JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
		jobDao.resetJob();
		
		try {
			Thread.sleep(INTERVAL);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
