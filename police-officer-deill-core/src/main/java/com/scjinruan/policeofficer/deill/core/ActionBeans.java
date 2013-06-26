package com.scjinruan.policeofficer.deill.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/**
 * 启动后则从actionbean.xml中加载Action实现类
 *
 */
public class ActionBeans {
	public static final Logger logger=Logger.getLogger(ActionBeans.class);
	private static Map<String,Action>  actionMap=null;
	/**
	 * 根据传入的ID返回Action处理类
	 * @param id
	 * @return
	 */
	public Action findById(String id){
		return actionMap.get(id);
	}
	
	/**
	 * 初始化配置类
	 * @param confFile 配置文件名称,例如actionbean.xml
	 */
	public ActionBeans(String confFile){
		File file=new File(confFile);
		if(!file.exists()){
			logger.info("业务配置文件"+file.getAbsolutePath()+"不存在,系统不能启动");
			System.exit(1);
		}
		try {
			SAXParserFactory factory=SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			parser.parse(file, new DefaultHandler(){
				@Override
				public void startElement(String uri, String localName,
						String qName, Attributes attr)
						throws SAXException {
					if(qName.equalsIgnoreCase("action")){
						String reqid=attr.getValue("reqid");
						logger.debug("获取到ID配置:"+reqid);
						String clz=attr.getValue("class");
						logger.debug("获取到class配置:"+clz);
						String method=attr.getValue("method");
						actionMap=new HashMap<String, Action>();
						try {
							Object obj=Class.forName(clz).newInstance();
							if(method!=null&&obj instanceof DispatherAction){
								DispatherAction da=(DispatherAction)obj;
								da.setMethod(da.getClass().getMethod(method,Bunlde.class));
								actionMap.put(reqid, da);
							}else{
								Action action=(Action)obj;
								actionMap.put(reqid, action);
							}
						} catch (InstantiationException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							e.printStackTrace();
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
						} catch (SecurityException e) {
							e.printStackTrace();
						}
					}
				}
			});
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
