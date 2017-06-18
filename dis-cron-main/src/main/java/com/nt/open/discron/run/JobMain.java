/**
 * 
 */
package com.nt.open.discron.run;

import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;

import com.nt.open.discron.run.netty.NettyServer;

/**
 * @author bjfulianqiu
 *
 */
public class JobMain {

	public static void main(String[] args) {
//		URL url=Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
//		DOMConfigurator.configure(url);
		
		NettyServer nettyServer=new NettyServer();
		nettyServer.init();
		nettyServer.start();
		
		JobManager jobManager=new JobManager();
		jobManager.run();
	}
}
