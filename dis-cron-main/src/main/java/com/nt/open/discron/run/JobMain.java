/**
 * 
 */
package com.nt.open.discron.run;

import com.nt.open.discron.run.netty.NettyServer;

/**
 * @author bjfulianqiu
 *
 */
public class JobMain {

	public static void main(String[] args) {
		//启动netty服务，接收proc返回值
		NettyServer nettyServer=new NettyServer();
		nettyServer.init();
		nettyServer.start();
		
		//启动监控超时线程
		ProcMonitor procMonitor=new ProcMonitor();
		Thread procMonitorThread=new Thread(procMonitor);
		procMonitorThread.start();
		
		//检查是有些job因为宕机而任务没有机器执行
		ResetJob resetJob=new ResetJob();
		Thread resetJobThread=new Thread(resetJob);
		resetJobThread.start();
		
		//启动job主进程
		JobManager jobManager=new JobManager();
		jobManager.run();
	}
}
