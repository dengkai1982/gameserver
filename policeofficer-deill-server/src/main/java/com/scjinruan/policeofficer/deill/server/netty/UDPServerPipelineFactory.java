package com.scjinruan.policeofficer.deill.server.netty;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
public class UDPServerPipelineFactory implements ChannelPipelineFactory {
	private String charset="gbk";
	public UDPServerPipelineFactory(String charset){
		this.charset=charset;
	}
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		System.out.println(charset);
		ChannelPipeline pipeline=Channels.pipeline();
		pipeline.addLast("decoder", new AMF3Decoder());
        pipeline.addLast("encoder", new AMF3Encoder());
        pipeline.addLast("handler", new UDPServerHandler());
        return pipeline;
	}
}
