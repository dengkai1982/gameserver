package com.scjinruan.policeofficer.deill.server.netty;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;
import flex.messaging.io.SerializationContext;
import flex.messaging.io.amf.Amf3Input;
/**
 * AMF3编码
 * @author Administrator
 *
 */
public class AMF3Decoder extends FrameDecoder{

	@Override
	protected Object decode(ChannelHandlerContext context, Channel channel,
			ChannelBuffer buffer) throws Exception {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		while(buffer.readable()){
			out.write(buffer.readByte());
		}
		SerializationContext sc=new SerializationContext();
		Amf3Input input=new Amf3Input(sc);
		ByteArrayInputStream bais=new ByteArrayInputStream(out.toByteArray());
		input.setInputStream(bais);
		@SuppressWarnings("unchecked")
		Map<String, Object> map=(Map<String, Object>)input.readObject();
		input.close();
		return map;
	}

}
