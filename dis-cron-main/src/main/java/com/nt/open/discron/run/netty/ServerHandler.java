/**
 * 
 */
package com.nt.open.discron.run.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fulianqiu
 *
 */
public class ServerHandler extends SimpleChannelHandler {
	
	private static Logger logger = LoggerFactory.getLogger("discron");
	
	private static ServerHandler serverHandler;
	private ServerHandler(){}

	public static ServerHandler getServerHandler(){
		if(serverHandler==null){
			synchronized(ServerHandler.class){
				if(serverHandler==null){
					serverHandler=new ServerHandler();
				}
			}
		}
		return serverHandler;
	}
	/** 
	 * 关键方法 
	 * 用于接收从客户端发来的消息，进行相应的逻辑处理 
	 */
	@Override  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {
		logger.info("server receive message"+e.getMessage());
//		while(true){
//			e.getChannel().write("server 1");
//			Thread.sleep(1000);
//		}
    }
}
