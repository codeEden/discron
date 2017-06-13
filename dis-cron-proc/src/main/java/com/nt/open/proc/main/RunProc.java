/**
 * 
 */
package com.nt.open.proc.main;

import com.nt.open.proc.entity.emun.JobEnum;
import com.nt.open.proc.util.AppContext;
import com.nt.open.proc.util.HTTPUtils;
import com.nt.open.proc.util.LogUtil;

/**
 * @author bjfulianqiu
 *
 */
public class RunProc {
	

	public static void main(String[] args) {
		try{
			String jobName=args[0];
			String rootPath=args[1];
			String url=args[2];
			String typeStr=args[3];
			String timeout=args[4];
			String nettyPort=args[5];
			
			AppContext.APPCONTEXT.setJobName(jobName);
			AppContext.APPCONTEXT.setNettyPort(Integer.parseInt(nettyPort));
			AppContext.APPCONTEXT.setRootPath(rootPath);
			
			LogUtil.info("runProc start....jobName="+args[0]);
			
			
			
			
			LogUtil.info("runProc end....jobName="+args[0]);
		}catch(Exception e){
			e.printStackTrace();
		}
		System.exit(0);
	}
}
