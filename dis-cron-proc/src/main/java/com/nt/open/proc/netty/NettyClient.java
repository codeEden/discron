/**
 * 
 */
package com.nt.open.proc.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

import com.nt.open.proc.util.AppContext;

/**
 * @author bjfulianqiu
 *
 */
public class NettyClient {

	private static ChannelFactory factory=null;
	private static ClientBootstrap bootstrap =null;
	private static final String host = "127.0.0.1";
	private static ChannelFuture f =null;
	
	static {

		BlockingQueue<Runnable> queue=new LinkedBlockingQueue<Runnable>(100);
		RejectedExecutionHandler rejectHandler=new ThreadPoolExecutor.CallerRunsPolicy();
		ThreadPoolExecutor bossExecutor=new ThreadPoolExecutor(10, 50, 0L, TimeUnit.MILLISECONDS, queue, rejectHandler);
		ThreadPoolExecutor workerExecutor=new ThreadPoolExecutor(10, 50, 0L, TimeUnit.MILLISECONDS, queue, rejectHandler);
		
		factory = new NioClientSocketChannelFactory(bossExecutor, workerExecutor);

		bootstrap = new ClientBootstrap(factory);
		bootstrap.getPipeline().addLast("handler", ClientHandler.getServerHandler());
		bootstrap.getPipeline().addLast("encoder", new StringEncoder());
		bootstrap.getPipeline().addLast("decoder", new StringDecoder());
		bootstrap.setOption("connectTimeoutMillis", 10 * 1000);
		bootstrap.setOption("tcpNoDelay", true);
		bootstrap.setOption("keepAlive", false);
		f = bootstrap.connect(new InetSocketAddress(host, AppContext.APPCONTEXT.getNettyPort()));
	}
	
	public static void sendMessage(String message){
		f.getChannel().write(message);
	}
	
	public static void main(String[] args) {
		sendMessage("client test11111");
	}
}
