/**
 * 
 */
package com.nt.open.discron.run.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nt.open.discron.util.AppContext;

/**
 * @author fulianqiu
 *
 */
public class NettyServer {
	
	private static Logger logger = LoggerFactory.getLogger("discron");

	private ServerBootstrap bootstrap;
	private ServerHandler handler;

	public void init() {
		int cpuNum=Runtime.getRuntime().availableProcessors(); 
		BlockingQueue<Runnable> queue=new LinkedBlockingQueue<Runnable>(200);
		RejectedExecutionHandler rejectHandler=new ThreadPoolExecutor.CallerRunsPolicy();
		ThreadPoolExecutor bossExecutor=new ThreadPoolExecutor(cpuNum+2, 50, 0L, TimeUnit.MILLISECONDS, queue, rejectHandler);
		ThreadPoolExecutor workerExecutor=new ThreadPoolExecutor(cpuNum+2, 50, 0L, TimeUnit.MILLISECONDS, queue, rejectHandler);
		
		bootstrap = new ServerBootstrap(new NioServerSocketChannelFactory(  
				bossExecutor, //boss 监听请求，并分派给slave进行处理  
				workerExecutor//slave 处理请求，将其丢到线程池中处理  
                                         ));
		handler = ServerHandler.getServerHandler();

		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				/* 典型的过滤式处理 */
				pipeline.addLast("encode", new StringEncoder());
				pipeline.addLast("decode", new StringDecoder());
				/* 添加自定义的handler，对请求进行处理 */
				pipeline.addLast("handler", handler);
				return pipeline;
			}
		});

		/* 使用tcp长连接 */
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.setOption("reuseAddress", true);
	}

	/**
	 * 绑定端口，启动netty服务
	 */
	public void start() {
		bootstrap.bind(new InetSocketAddress(AppContext.APPCONTEXT.NETTY_SERVER_PORT));
		logger.info("服务器启动,端口:{}" , AppContext.APPCONTEXT.NETTY_SERVER_PORT);
	}
	
	public static void main(String[] args) {
		NettyServer nettyServer=new NettyServer();
		nettyServer.init();
		nettyServer.start();
	}

	/**
	 * 关闭netty，释放资源。
	 */
	public void stop() {
		bootstrap.releaseExternalResources();
	}
}
