/**
 * 
 */
package com.nt.open.discron.run;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.dao.JobHisDao;
import com.nt.open.discron.entity.ProcInfo;
import com.nt.open.discron.entity.po.JobHisPO;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.log.LogUtil;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.util.AppContext;

/**
 * @author bjfulianqiu
 *
 */
public class ProcMonitor implements Runnable {

	private final int BUFFERTIME=1000;
	
	private final long CHECK_INTERVAL=2*1000L;
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while(true){
			List<ProcInfo> procList=AppContext.APPCONTEXT.getJobProcList();
			LogUtil.info("job进程监控,procList=={}", procList.size());
			if(!CollectionUtils.isEmpty(procList)){
				Date now=new Date();
				Iterator<ProcInfo> iterator=procList.iterator();
				while(iterator.hasNext()){
					ProcInfo procInfo=iterator.next();
					Integer timeout=procInfo.getTimeout();
					Long startTime=procInfo.getStartTime();
					Long runTime=now.getTime()-startTime;
					//如果当前时间-开始时间>超时时间(预留buffer)，则超时处理
					if(runTime.longValue()>(timeout.intValue()+BUFFERTIME)){
						timeOutHandler(procInfo, runTime,timeout);
						iterator.remove();
					}
				}
			}
			
			try {
				Thread.sleep(CHECK_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
				LogUtil.error("proc monitor sleep fail ", e);
			}
		}
	}
	
	private void timeOutHandler(ProcInfo procInfo,Long runTime,Integer timeout){
		Long id=procInfo.getJobId();
		Process process=procInfo.getProcess();
		JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
		JobPO jobPO=jobDao.get(id);
		if(jobPO!=null){
			JobHisPO jobHisPO=new JobHisPO(jobPO);
			jobHisPO.setExecuteTimes(runTime);
			jobHisPO.setCreateTime(new Date());
			jobHisPO.setErrorMsg("time out kill proc");
			jobHisPO.setJobId(id);
			//写入历史记录
			JobHisDao jobHisDao=(JobHisDao) ProxyUtil.getProxy(JobHisDao.class);
			jobHisDao.insert(jobHisPO);
		}
		process.destroy();
		
		LogUtil.info("time out kill proc,id=={},runTime={},timeout={}", id,runTime,timeout);
	}

}
