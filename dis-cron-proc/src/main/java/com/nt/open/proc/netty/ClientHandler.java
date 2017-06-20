/**
 * 
 */
package com.nt.open.proc.netty;

import org.jboss.netty.buffer.BigEndianHeapChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.alibaba.fastjson.JSON;
import com.nt.open.proc.entity.dto.Message;
import com.nt.open.proc.util.AppContext;
import com.nt.open.proc.util.LogUtil;

/**
 * @author fulianqiu
 *
 */
public class ClientHandler extends SimpleChannelHandler {
	
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
		  LogUtil.info("client channel closed");
	  }

	  @Override
	  public void channelDisconnected(
	      ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		  LogUtil.info("client channel disconnect for peer");
	  }

	  
	/** 
	 * 关键方法 
	 * 用于接收从客户端发来的消息，进行相应的逻辑处理 
	 */
	@Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)  
            throws Exception {
		Object msgObj=e.getMessage();
		LogUtil.info("msgObj=="+msgObj);
		if(msgObj!=null){
			BigEndianHeapChannelBuffer BigEndianHeapChannelBuffe=(BigEndianHeapChannelBuffer)msgObj;
			byte[] req = new byte[BigEndianHeapChannelBuffe.readableBytes()];
			BigEndianHeapChannelBuffe.readBytes(req);
			String body = new String(req, "UTF-8");
			
			LogUtil.info("client receive message"+body);
			Message message=(Message) JSON.parseObject(body, Message.class);
			if(message.getCode()==200){
				AppContext.APPCONTEXT.setOver(true);
			}
		}
		
    }
}
