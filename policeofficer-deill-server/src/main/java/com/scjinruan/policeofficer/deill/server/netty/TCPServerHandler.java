package com.scjinruan.policeofficer.deill.server.netty;
import java.net.InetSocketAddress;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import com.scjinruan.policeofficer.deill.core.Action;
import com.scjinruan.policeofficer.deill.core.Bunlde;
import com.scjinruan.policeofficer.deill.core.RequestPaser;
import com.scjinruan.policeofficer.deill.core.RequestService;
import com.scjinruan.policeofficer.deill.server.ServerContext;
public class TCPServerHandler extends SimpleChannelHandler {
	private static Logger logger=Logger.getLogger(TCPServerHandler.class);
	private ServerContext context=ServerContext.getInstance();
	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		// 客户端连接
		InetSocketAddress clientAddr=(InetSocketAddress) e.getChannel().getRemoteAddress();
		logger.debug(clientAddr.getHostString()+":"+clientAddr.getHostName());
		logger.debug("TCP客户端链接.远程地址 :"+clientAddr);
	}
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		// 消息处理
		@SuppressWarnings("unchecked")
		Map<String, Object> request=(Map<String, Object>) e.getMessage();
		Action action=RequestPaser.paser(request, 
				context.getQueryFiledName(),
				context.getBeans());
		Bunlde bunlde=new Bunlde(request, context.getDataSource(), ctx, e);
		RequestService service=new TCPRequestService(action,bunlde,e);
		service.doService();
	}
	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e)
			throws Exception {
		super.channelClosed(ctx, e);
		//客户端退出
		Channel channel=e.getChannel();
		logger.info("客户端退出.RemoteAddress :"+channel.getRemoteAddress());
		channel.close();
	}
}
