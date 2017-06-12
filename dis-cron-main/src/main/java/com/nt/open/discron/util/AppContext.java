/**
 * 
 */
package com.nt.open.discron.util;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * @author bjfulianqiu
 *
 */
public enum AppContext {
	
	APPCONTEXT;

	private Map<String, Process> jobProcMap=Maps.newConcurrentMap();
	
	public final int NETTY_SERVER_PORT=8798;
	
	
	public void addJobProcMap(String jobName,Process process){
		jobProcMap.put(jobName, process);
	}
}
