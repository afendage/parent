package com.common;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * JDBC 各类数据库连接
 * @author afen
 *
 */
public class DbConnectionUtil {

	/**
	 * mysql 连接方式
	 * @param user
	 * @param pwd
	 * @param url
	 * @return
	 */
	public Connection getMysqlConn(String user, String pwd, String url) {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * sqlserver连接方式
	 * @param user
	 * @param pwd
	 * @param url
	 * @return
	 */
	public Connection getsqlConn(String user, String pwd, String url) {
		Connection conn = null;
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			conn = DriverManager.getConnection(url, user, pwd);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}
}
