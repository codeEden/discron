/**
 * 
 */
package com.nt.open.discron.util;

import java.util.Map;
import java.util.concurrent.ConcurrentMap;

import com.google.common.collect.Maps;
import com.nt.open.discron.entity.ProcInfo;
import com.nt.open.discron.log.LogUtil;

/**
 * @author bjfulianqiu
 *
 */
public enum AppContext {
	
	APPCONTEXT;

	private ConcurrentMap<String, ProcInfo> jobProcMap=Maps.newConcurrentMap();
	
	public final int NETTY_SERVER_PORT=8798;
	
	
	public void addJobProcMap(String key,ProcInfo process){
		LogUtil.info("addProcMap,key={}", key);
		jobProcMap.putIfAbsent(key, process);
	}
	
	public Map<String, ProcInfo> getJobProcList(){
		return this.jobProcMap;
	}
	
	public boolean removeJobProc(String key){
		jobProcMap.remove(key);
		return true;
	}
}
