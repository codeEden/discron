/**
 * 
 */
package com.nt.open.discron.run;

import java.net.URL;

import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author bjfulianqiu
 *
 */
public class JobMain {

	public static void main(String[] args) {
//		URL url=Thread.currentThread().getContextClassLoader().getResource("log4j.xml");
//		DOMConfigurator.configure(url);
		
		JobManager jobManager=new JobManager();
		jobManager.run();
	}
}
