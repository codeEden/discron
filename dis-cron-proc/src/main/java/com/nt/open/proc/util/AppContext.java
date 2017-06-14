/**
 * 
 */
package com.nt.open.proc.util;

/**
 * @author bjfulianqiu
 *
 */
public enum AppContext {
	
	APPCONTEXT;

	private int nettyPort=8798;
	
	private String rootPath="";
	
	private String jobName="";
	
	private boolean isOver=false;

	public int getNettyPort() {
		return nettyPort;
	}

	public void setNettyPort(int nettyPort) {
		this.nettyPort = nettyPort;
	}

	public String getRootPath() {
		return rootPath;
	}
	public void setRootPath(String rootPath) {
		this.rootPath = rootPath;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public boolean isOver() {
		return isOver;
	}

	public void setOver(boolean isOver) {
		this.isOver = isOver;
	}
	
	
}
