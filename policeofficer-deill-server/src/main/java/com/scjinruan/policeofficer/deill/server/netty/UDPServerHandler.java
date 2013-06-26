package com.scjinruan.policeofficer.deill.server.netty;
import java.util.Map;
import org.apache.log4j.Logger;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;

import com.scjinruan.policeofficer.deill.core.Action;
import com.scjinruan.policeofficer.deill.core.Bunlde;
import com.scjinruan.policeofficer.deill.core.RequestPaser;
import com.scjinruan.policeofficer.deill.core.RequestService;
import com.scjinruan.policeofficer.deill.server.ServerContext;

public class UDPServerHandler extends SimpleChannelUpstreamHandler {
	private static Logger logger=Logger.getLogger(UDPServerHandler.class);
	private ServerContext context=ServerContext.getInstance();
	@Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
            throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> request=(Map<String, Object>) e.getMessage();
		logger.debug(e.getRemoteAddress());
		request.put("remote_client_address", e.getRemoteAddress());
		Action action=RequestPaser.paser(request,context.getQueryFiledName(),context.getBeans());
		Bunlde bunlde=new Bunlde(request,ServerContext.getInstance().getDataSource(),ctx,e);
		RequestService service=new UDPRequestService(action,bunlde,e);
		service.doService();
    }
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e)
            throws Exception {
        e.getCause().printStackTrace();
    }
}
