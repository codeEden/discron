/**
 * 
 */
package com.nt.open.discron.run;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.dao.JobHisDao;
import com.nt.open.discron.entity.ProcInfo;
import com.nt.open.discron.entity.po.JobHisPO;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.log.LogUtil;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.quartz.JobFactory;
import com.nt.open.discron.util.AppContext;
import com.nt.open.discron.util.HostHelper;

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
			Map<Long,ProcInfo> procMap=AppContext.APPCONTEXT.getJobProcList();
			LogUtil.info("job进程监控,procList=={}", procMap.size());
			if(!MapUtils.isEmpty(procMap)){
				Date now=new Date();
				Set<Entry<Long, ProcInfo>> entrySet=procMap.entrySet();
				for(Entry<Long,ProcInfo> entry:entrySet){
					ProcInfo procInfo=entry.getValue();
					Integer timeout=procInfo.getTimeout();
					Long startTime=procInfo.getStartTime();
					Long runTime=now.getTime()-startTime;
					//如果当前时间-开始时间>超时时间(预留buffer)，则超时处理
					if(runTime.longValue()>(timeout.intValue()+BUFFERTIME)){
						timeOutHandler(procInfo, runTime,timeout);
						procMap.remove(entry.getKey());
					}
				}
			}
			
			//检查quartz中job是否已经被转移
			checkQuartzJob();
			
			try {
				Thread.sleep(CHECK_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
				LogUtil.error("proc monitor sleep fail ", e);
			}
		}
	}
	
	/**
	 * 检查quartz中job是否已经被转移
	 */
	private void checkQuartzJob(){
		List<Long> idList=JobFactory.JOBFACTORY.getAllJobKey();
		LogUtil.info("检查quartz中job是否已经被转移,ids={}",idList);
		JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
		List<JobPO> jobList=jobDao.getListByIds(idList);
		if(!CollectionUtils.isEmpty(jobList)){
			for(JobPO jobPO:jobList){
				String handleHost=jobPO.getHandleHost();
				if(!HostHelper.getHostName().equals(handleHost)){
					JobFactory.JOBFACTORY.deleteJob(String.valueOf(jobPO.getId()));
					LogUtil.info("检查quartz中job是否已经被转移,removeId={}",jobPO.getId());
				}
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
