package com.scjinruan.policeofficer.deill.server.netty;

import java.net.InetSocketAddress;
import org.apache.log4j.Logger;
import org.jboss.netty.bootstrap.ConnectionlessBootstrap;
import org.jboss.netty.channel.FixedReceiveBufferSizePredictorFactory;
import org.jboss.netty.channel.socket.DatagramChannelFactory;
import org.jboss.netty.channel.socket.nio.NioDatagramChannelFactory;

import com.scjinruan.policeofficer.deill.server.Server;

/**
 * UDP服务实现
 * @author Administrator
 *
 */
public class UDPServer implements Server {
	private static Logger logger=Logger.getLogger(UDPServer.class);
	/**
	 * 端口
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
	
	private ConnectionlessBootstrap bootstrap;
	
	public UDPServer(int port, int bufSize, String charset) {
		super();
		this.port = port;
		this.bufSize = bufSize;
		this.charset = charset;
	}

	@Override
	public void launch() {
		DatagramChannelFactory datagram=new NioDatagramChannelFactory();
		bootstrap=new ConnectionlessBootstrap(datagram);
		logger.info("设置UDP服务监听端口:"+port);
		logger.info("设置UDP服务缓冲大小:"+bufSize);
		logger.info("设置UDP服务字符集:"+charset);
		bootstrap.setPipelineFactory(new UDPServerPipelineFactory(charset));
		// Enable broadcast
		bootstrap.setOption("broadcast", "false");
		bootstrap.setOption(
                "receiveBufferSizePredictorFactory",
                new FixedReceiveBufferSizePredictorFactory(bufSize));
        // Bind to the port and start the service.
		bootstrap.bind(new InetSocketAddress(port));
		logger.info("UDP服务已启动,启动端口:"+port);
	}

	@Override
	public void destory() {
		if(bootstrap!=null){
			logger.info("尝试关闭UDP服务");
			bootstrap.shutdown();
			logger.info("UDP服务已关闭");
		}
	}

}
