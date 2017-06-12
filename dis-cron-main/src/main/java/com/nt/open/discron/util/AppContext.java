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
public class AppContext {

	public static Map<String, String> jobMap=Maps.newConcurrentMap();
	
	public void addJobMap(String jobName){
		jobMap.put(jobName, jobName);
	}
}
