package com.scjinruanpoliceofficer.deill.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * JDBC工具类
 *
 */
public class JDBCUtil {
	/**
	 * 关闭数据库Connection
	 * @param o
	 */
	public static final void close(Connection o){
		if(o!=null){
			try {
				o.close();
				o=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭数据库Statement
	 * @param o
	 */
	public static final void close(Statement o){
		if(o!=null){
			try {
				o.close();
				o=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭数据库ResultSet
	 * @param o
	 */
	public static final void close(ResultSet o){
		if(o!=null){
			try {
				o.close();
				o=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 关闭数据库PreparedStatement
	 * @param o
	 */
	public static final void close(PreparedStatement o){
		if(o!=null){
			try {
				o.close();
				o=null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
