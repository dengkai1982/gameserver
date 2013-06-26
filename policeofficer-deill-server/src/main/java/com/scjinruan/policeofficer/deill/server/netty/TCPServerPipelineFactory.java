package com.scjinruan.policeofficer.deill.server.netty;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
public class TCPServerPipelineFactory implements ChannelPipelineFactory {
	private int bufSize=8192;
	private String charset="gbk";
	public TCPServerPipelineFactory(int bufSize,String charset){
		this.bufSize=bufSize;
		this.charset=charset;
	}
	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline=Channels.pipeline();
		System.out.println(bufSize);
		System.out.println(charset);
		/*pipeline.addLast("framer", new DelimiterBasedFrameDecoder(
                bufSize, Delimiters.lineDelimiter()));*/
		pipeline.addLast("decoder", new AMF3Decoder());
		pipeline.addLast("encoder", new AMF3Encoder());
		pipeline.addLast("handler", new TCPServerHandler());
		/*
		Charset cs=Charset.forName(charset);
        pipeline.addLast("decoder", new StringDecoder(cs));
        pipeline.addLast("encoder", new StringEncoder(cs));
        pipeline.addLast("handler", new TCPServerHandler());*/
        return pipeline;
	}
}
