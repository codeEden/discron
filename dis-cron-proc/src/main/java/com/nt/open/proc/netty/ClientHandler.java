/**
 * 
 */
package com.nt.open.proc.netty;

import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nt.open.proc.util.LogUtil;

/**
 * @author fulianqiu
 *
 */
public class ClientHandler extends SimpleChannelHandler {
	
	private static Logger logger = LoggerFactory.getLogger("discron");
	
	private ChannelFuture future;
	
	private static ClientHandler serverHandler;
	private ClientHandler(){}

	public static ClientHandler getServerHandler(){
		if(serverHandler==null){
			synchronized(ClientHandler.class){
				if(serverHandler==null){
					serverHandler=new ClientHandler();
				}
			}
		}
		return serverHandler;
	}
	@Override
	  public void channelConnected(
	      ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		String msg=e.getValue().toString();
	    LogUtil.info("建立连接:" + msg);
	  }

	  @Override
	  public void channelClosed(
	      ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
	    System.out.println("client channel closed");
	  }

	  @Override
	  public void channelDisconnected(
	      ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
	    System.out.println("client channel disconnect for peer");
	  }

	  
	/** 
	 * 关键方法 
	 * 用于接收从客户端发来的消息，进行相应的逻辑处理 
	 */
	@Override  
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {
		logger.info("client receive message"+e.getMessage().toString());
//		e.getChannel().write("client 1");
    }
}
