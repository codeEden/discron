/**
 * 
 */
package com.nt.open.discron.util;

import java.util.List;

import com.google.common.collect.Lists;
import com.nt.open.discron.entity.ProcInfo;

/**
 * @author bjfulianqiu
 *
 */
public enum AppContext {
	
	APPCONTEXT;

	private List<ProcInfo> jobProcList=Lists.newArrayList();
	
	public final int NETTY_SERVER_PORT=8798;
	
	
	public void addJobProcList(ProcInfo process){
		jobProcList.add(process);
	}
	
	public List<ProcInfo> getJobProcList(){
		return this.jobProcList;
	}
}
