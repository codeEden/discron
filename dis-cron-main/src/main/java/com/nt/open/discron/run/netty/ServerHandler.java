/**
 * 
 */
package com.nt.open.discron.run.netty;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * @author fulianqiu
 *
 */
public class ServerHandler extends SimpleChannelHandler {

	/** 
	 * 关键方法 
	 * 用于接收从客户端发来的消息，进行相应的逻辑处理 
	 */
	@Override  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {  
    }
}
