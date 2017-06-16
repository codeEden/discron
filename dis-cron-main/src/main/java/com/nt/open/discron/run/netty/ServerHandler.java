/**
 * 
 */
package com.nt.open.discron.run.netty;

import java.util.Date;
import java.util.Map;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Strings;
import com.nt.open.discron.dao.JobDao;
import com.nt.open.discron.dao.JobHisDao;
import com.nt.open.discron.entity.dto.Message;
import com.nt.open.discron.entity.po.JobHisPO;
import com.nt.open.discron.entity.po.JobPO;
import com.nt.open.discron.mybatis.ProxyUtil;

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
		if(e.getMessage()!=null){
			String message=(String) e.getMessage();
			logger.info("server receive message"+message);
			if(!Strings.isNullOrEmpty(message)){
				Map<String, Object> msgMap=JSON.parseObject(message);
				String id=(String) msgMap.get("id");
				String errorMsg=(String) msgMap.get("errorMsg");
				long runTime=Long.parseLong((String) msgMap.get("runTime"));
				//获得代理类
				JobDao jobDao=(JobDao) ProxyUtil.getProxy(JobDao.class);
				JobPO job=jobDao.get(Long.parseLong(id));
				JobHisPO jobHisPO=new JobHisPO(job);
				jobHisPO.setExecuteTimes(runTime);
				jobHisPO.setCreateTime(new Date());
				jobHisPO.setErrorMsg(errorMsg);
				//写入历史记录
				JobHisDao jobHisDao=(JobHisDao) ProxyUtil.getProxy(JobHisDao.class);
				jobHisDao.insert(jobHisPO);
				
				
				Message retMessage=new Message();
				retMessage.setCode(200);
				retMessage.setMessage("success");
				e.getChannel().write(JSON.toJSONString(retMessage));
			}
		}
		
    }
}
