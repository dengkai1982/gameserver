package com.scjinruan.policeofficer.deill.core;
import java.util.Map;
/**
 * 请求解析器,根据不同的请求解析出处理过程Action
 *
 */
public class RequestPaser {
	/**
	 * 解析客户端请求
	 * @param request 客户端传入的Map
	 * @param queryFiledName 作为解析的Map中需要用到的对应的key字段名称
	 * @return
	 */
	public static Action paser(Map<String, Object> request,String queryFiledName,ActionBeans beans){
		String q=(String) request.get(queryFiledName);
		return beans.findById(q);
	}
}