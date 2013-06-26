package com.scjinruan.policeofficer.deill.server;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import com.scjinruan.policeofficer.deill.core.ActionBeans;

/**
 * 服务器上下文
 *
 */
public class ServerContext {
	protected  DataSource dataSource;
	
	protected static final ServerContext context=new ServerContext();
	
	protected Set<ClientInfo> clientSet=new HashSet<ClientInfo>();
	
	/*与AMF3交互的请求查询字段名称*/
	protected String queryFiledName;
	
	protected ActionBeans beans;
	
	protected ServerContext(){
		
	}
	/**
	 * 获取服务器上下文实例
	 * @return
	 */
	public static ServerContext getInstance(){
		return context;
	}
	public DataSource getDataSource(){
		return dataSource;
	}
	public String getQueryFiledName() {
		return queryFiledName;
	}
	public ActionBeans getBeans() {
		return beans;
	}
	/**
	 * 注册客户端
	 * @param info
	 */
	public void registClient(ClientInfo info){
		clientSet.add(info);
	}
	/**
	 * 移除客户端
	 * @param info
	 * @return
	 */
	public void removeClient(ClientInfo info){
		clientSet.remove(info);
	}
	/**
	 * 注册客户端UDP端口
	 * @param clientIp
	 * @param port
	 * @return 注册成功返回true,找不到客户端IP返回false
	 */
	public boolean registClientUdpPort(String clientIp,int port){
		for(ClientInfo info:clientSet){
			if(info.getIpaddr().equals(clientIp)){
				info.setUdpPort(port);
				return true;
			}
		}
		return false;
	}
}
