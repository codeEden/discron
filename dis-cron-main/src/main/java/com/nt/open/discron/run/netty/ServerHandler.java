/**
 * 
 */
package com.nt.open.discron.run.netty;

import java.util.Date;
import java.util.Map;

import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.dao.JobHisDao;
import com.nt.open.discron.entity.dto.Message;
import com.nt.open.discron.entity.po.JobHisPO;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.log.LogUtil;
import com.nt.open.discron.mybatis.ProxyUtil;
import com.nt.open.discron.util.AppContext;

/**
 * @author fulianqiu
 *
 */
public class ServerHandler extends SimpleChannelHandler {
	
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
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent me)  
            throws Exception {
		if(me.getMessage()!=null){
			String message=(String) me.getMessage();
			LogUtil.info("server receive message"+message);
			if(!Strings.isNullOrEmpty(message)){
				try{
					Map<String, Object> msgMap=JSON.parseObject(message);
					String id=(String) msgMap.get("id");
					String errorMsg=(String) msgMap.get("errorMsg");
					String procId=(String) msgMap.get("procId");
					long runTime=Long.parseLong(String.valueOf(msgMap.get("runTime")));
					//获得代理类
					JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
					JobPO job=jobDao.get(Long.parseLong(id));
					JobHisPO jobHisPO=new JobHisPO(job);
					jobHisPO.setExecuteTimes(runTime);
					jobHisPO.setCreateTime(new Date());
					jobHisPO.setErrorMsg(errorMsg);
					jobHisPO.setJobId(Long.parseLong(id));
					//写入历史记录
					JobHisDao jobHisDao=(JobHisDao) ProxyUtil.getProxy(JobHisDao.class);
					jobHisDao.insert(jobHisPO);
					
					AppContext.APPCONTEXT.removeJobProc(procId);
				}catch(Exception e){
					LogUtil.error("任务回写db失败", e);
				}
				
				Message retMessage=new Message();
				retMessage.setCode(200);
				retMessage.setMessage("success");
				LogUtil.info("回写client");
				ctx.getChannel().write(JSON.toJSONString(retMessage));
				
			}
		}
		
    }
	
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		super.channelClosed(ctx, e);
		LogUtil.info("channel 关闭");
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
		super.exceptionCaught(ctx, e);
		Channel channel = e.getChannel();  
        channel.close();  
        LogUtil.info("一个客户端退出："+channel.getId());  
	}
}
