package com.scjinruan.policeofficer.deill.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;
import com.scjinruan.policeofficer.deill.core.ActionBeans;
import com.scjinruan.policeofficer.deill.dao.impl.C3P0DSImpl;
import com.scjinruan.policeofficer.deill.dao.impl.DBCPDSImpl;
import com.scjinruan.policeofficer.deill.server.netty.TCPServer;
import com.scjinruan.policeofficer.deill.server.netty.UDPServer;
import com.scjinruanpoliceofficer.deill.dao.DataSourceFactory;
public class Bootstarp extends ServerContext {
	private static final Logger logger = Logger.getLogger(Bootstarp.class);

	public void initAndStart(String path) {
		logger.info("服务器开始启动");
		logger.info("读取配置文件");
		// 初始化数据源
		String cp = System.getProperty("user.dir") + "/conf/server.properties";
		if (path != null) {
			cp = path;
		}
		Properties prop = new Properties();
		FileInputStream in = null;
		try {
			in = new FileInputStream(cp);
			prop.load(in);
			String type=prop.getProperty("pool_type");
			DataSourceFactory factory=null;
			String driverClass=prop.getProperty("driverClass");
			String jdbcUrl=prop.getProperty("jdbcUrl");
			String username=prop.getProperty("username");
			String password=prop.getProperty("password");
			String data_pool_path=prop.getProperty("data_pool_path");
			logger.info("初始化数据源");
			if(type.equalsIgnoreCase("dbcp")){
				logger.info("数据源类型:DBCP");
				factory=new DBCPDSImpl(driverClass, jdbcUrl, username, password);
			}else{
				logger.info("数据源类型:C3P0");
				factory=new C3P0DSImpl(driverClass, jdbcUrl, username, password);
			}
			logger.debug(data_pool_path);
			factory.setPoolConfig(new File(data_pool_path));
			logger.info("数据源初始化完毕");
			logger.info("初始化业务逻辑类定义");
			String actions_path=prop.getProperty("actions_path");
			String request_code=prop.getProperty("request_code");
			ActionBeans beans=new ActionBeans(actions_path);
			ServerContext context=ServerContext.getInstance();
			context.beans=beans;
			context.dataSource=factory.getDataSource();
			context.queryFiledName=request_code;
			logger.info("业务逻辑类定义初始化完毕");
			logger.info("启动Tcp监听");
			String tcp_port=prop.getProperty("tcp_port");
			String udp_port=prop.getProperty("udp_port");
			Server tcp=new TCPServer(Integer.parseInt(tcp_port), 1024, "gbk");
			tcp.launch();
			logger.info("启动Tcp监听");
			Server udp=new UDPServer(Integer.parseInt(udp_port), 1024, "gbk");
			udp.launch();
			Runtime.getRuntime().addShutdownHook(new ServerHook(new Server[]{tcp,udp},factory));
		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException", e);
		} catch (IOException e) {
			logger.error("IOException", e);
		} catch (SQLException e) {
			logger.error("SQLException", e);
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
	}

	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			new Bootstarp().initAndStart(args[0]);
		} else {
			//发布时开启new Bootstarp().initAndStart(null)
			//new Bootstarp().initAndStart(null);
			new Bootstarp().initAndStart("E:\\PoliceOfficerProject\\policeofficer-deill-server\\src\\main\\resources\\server.properties");
		}
	}
	public static class ServerHook extends Thread{
		private Server[] servers;
		private DataSourceFactory factory;
		public ServerHook(Server[] servers,DataSourceFactory factory){
			this.servers=servers;
		}
		@Override
		public void run() {
			for(Server server:servers){
				server.destory();
			}
			factory.destory();
		}
	}
}
