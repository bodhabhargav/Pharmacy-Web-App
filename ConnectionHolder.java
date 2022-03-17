package com.caseStudy.dbcon;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

public class ConnectionHolder {

	public static ConnectionHolder instance = new ConnectionHolder();
	private DataSource ds = null;

	private ConnectionHolder() {
		initAppDataSource();
	}

	public static ConnectionHolder getInstance() throws DBConnectionException {
		return instance;
	}

	public Connection getConnection() throws DBConnectionException {
		try {
			return ds.getConnection(); // here is the connection process will happen by reading ds initialized values .. 
		} catch (SQLException e) {
			throw new DBConnectionException(e);
		}
	}

	public DataSource getDataSource() {
		return ds;
	}
	
	public void dispose() throws DBConnectionException {
		BasicDataSource bds = (BasicDataSource) ds;
		try {
			bds.close();
		} catch (SQLException e) {
			throw new DBConnectionException("Unable to close the connection", e);
		}
	}

	private void initAppDataSource() throws DBConnectionException {

		try {
			Context  initContext = new InitialContext();
			Context envContext = (Context) initContext.lookup("java:/comp/env");
			ds = (DataSource) envContext.lookup("jdbc/MyDB");
		} catch (NamingException e) {
			throw new DBConnectionException("Unable to get datasource", e);

		}
	}

}
