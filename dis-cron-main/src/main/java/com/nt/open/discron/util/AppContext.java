/**
 * 
 */
package com.nt.open.discron.util;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;
import com.nt.open.discron.entity.ProcInfo;

/**
 * @author bjfulianqiu
 *
 */
public enum AppContext {
	
	APPCONTEXT;

	private ConcurrentMap<Long, ProcInfo> jobProcMap=Maps.newConcurrentMap();
	
	public final int NETTY_SERVER_PORT=8798;
	
	
	public void addJobProcMap(ProcInfo process){
		jobProcMap.putIfAbsent(process.getJobId(), process);
	}
	
	public Map<Long, ProcInfo> getJobProcList(){
		return this.jobProcMap;
	}
	
	public boolean removeJobProc(Long id){
		jobProcMap.remove(id);
		return true;
	}
}
