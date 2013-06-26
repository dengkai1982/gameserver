package com.scjinruan.policeofficer.deill.core;
/**
 * 处理过程动作
 * @author Administrator
 *
 */
public interface Action {
	/**
	 * 处理动作,处理完毕之后需要返回处理结果的Map封装
	 * @param dataSource
	 * @param request
	 */
	Bunlde execute(Bunlde bunlde);
}

