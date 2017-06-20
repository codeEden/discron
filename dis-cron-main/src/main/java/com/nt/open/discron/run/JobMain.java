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
		NettyServer nettyServer=new NettyServer();
		nettyServer.init();
		nettyServer.start();
		
		JobManager jobManager=new JobManager();
		jobManager.run();
	}
}
