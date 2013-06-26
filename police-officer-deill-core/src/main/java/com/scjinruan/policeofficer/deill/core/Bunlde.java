package com.scjinruan.policeofficer.deill.core;

import java.util.Map;

import javax.sql.DataSource;

import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
/**
 * 数据传递包
 * @author Administrator
 *
 */
public class Bunlde {
	//消息
	private Map<String, Object> clientMessage;
	//数据源
	private DataSource dataSource;
	//Netty上下文
	private ChannelHandlerContext context;
	//Netty事件
	private MessageEvent event;
	
	public Bunlde(){
		
	}
	public Bunlde(Map<String, Object> clientMessage, DataSource dataSource,
			ChannelHandlerContext context, MessageEvent event) {
		this.clientMessage = clientMessage;
		this.dataSource = dataSource;
		this.context = context;
		this.event = event;
	}

	public Map<String, Object> getClientMessage() {
		return clientMessage;
	}

	public void setClientMessage(Map<String, Object> clientMessage) {
		this.clientMessage = clientMessage;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public ChannelHandlerContext getContext() {
		return context;
	}

	public MessageEvent getEvent() {
		return event;
	}
}
