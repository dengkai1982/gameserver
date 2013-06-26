package com.scjinruan.policeofficer.deill.server.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import com.scjinruan.policeofficer.deill.server.Server;
/**
 * TCP服务实现
 * @author Administrator
 *
 */
public class TCPServer implements Server{
	private static final Logger logger = Logger.getLogger(TCPServer.class);
	/**
	 * 启动端口
	 */
	private final int port;
	/**
	 * 缓冲区大小
	 */
	private final int bufSize;
	/**
	 * 字符编码集
	 */
	private final String charset;
	
	private  ServerBootstrap bootstrap;
	
	public TCPServer(int port,int bufSize,String charset) {
		this.port = port;
		this.bufSize=bufSize;
		this.charset=charset;
	}
	@Override
	public void launch(){
		ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
		logger.info("设置TCP服务监听端口:"+port);
		logger.info("设置TCP服务缓冲大小:"+bufSize);
		logger.info("设置TCP服务字符集:"+charset);
		bootstrap.setPipelineFactory(new TCPServerPipelineFactory(bufSize,charset));
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bindAsync(new InetSocketAddress(port));
		logger.info("TCP服务已启动,启动端口:"+port);
	}

	@Override
	public void destory() {
		if(bootstrap!=null){
			logger.info("尝试关闭TCP服务");
			bootstrap.shutdown();
			logger.info("TCP服务已关闭");
		}
	}
}
