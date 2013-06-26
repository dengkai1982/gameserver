package com.scjinruan.policeofficer.deill.server.netty;

import java.io.ByteArrayOutputStream;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Output;
/**
 * AMF3编码
 * @author Administrator
 *
 */
public class AMF3Encoder extends SimpleChannelHandler{

	@Override
	public void writeRequested(ChannelHandlerContext ctx, MessageEvent e)
			throws Exception {
		SerializationContext context=new SerializationContext();
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		Amf3Output amfOut=new Amf3Output(context);
		amfOut.setOutputStream(out);
		amfOut.writeObject(e.getMessage());
		ChannelBuffer buffer=ChannelBuffers.copiedBuffer(out.toByteArray());
		amfOut.close();
		Channels.write(ctx,e.getFuture(),buffer,e.getRemoteAddress());
	}
}
