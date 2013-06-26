package com.scjinruan.policeofficer.deill.core;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
/**
 * 能够根据指定的方法调用的Action
 * 方法命名必须为
 * public Bunlde method_name(Bunlde bunlde)
 *
 */
public class DispatherAction implements Action{
	
	private Method method;
	
	public void setMethod(Method method){
		this.method=method;
	}
	@Override
	public Bunlde execute(Bunlde bunlde) {
		try {
			return (Bunlde) method.invoke(this, bunlde);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	protected Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}
}
