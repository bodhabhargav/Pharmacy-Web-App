package com.nttdata.casestudy.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;

import com.mysql.cj.jdbc.MysqlDataSource;

public class TestDataSource {
	
	DataSource ds = null;
	
	//@Before
	 public void setUp() throws Exception {
	        // rcarver - setup the jndi context and the datasource
	        try {
	            // Create initial context
	            System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
	            System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
	            InitialContext ic = new InitialContext();

	            ic.createSubcontext("java:");
	            ic.createSubcontext("java:/comp");
	            ic.createSubcontext("java:/comp/env");
	            ic.createSubcontext("java:/comp/env/jdbc");

	            MysqlDataSource ds = new MysqlDataSource();
	            ds.setURL("jdbc:mysql://localhost:3306/case_study");
	            ds.setUser("root");
	            ds.setPassword("1234");
	            ic.bind("java:/MyDB", ds);
	        } catch (NamingException ex) {
	            //Logger.getLogger(JunitDataSource.class.getName()).log(Level.SEVERE, null, ex);
	        	System.out.println("Exception: " + ex);
	        }

	        // Obtain our environment naming context
	        Context initCtx = new InitialContext();

	        // Look up our datasource
	        DataSource ds = (DataSource) initCtx.lookup("java:/MyDB");

	    }

	 @Test
	    public void testSimple() throws Exception {


	        Connection conn = ds.getConnection();
	        Statement stmt = conn.createStatement();

	        ResultSet rset = stmt.executeQuery("SELECT * from user");


	        while (rset.next()) {
	          System.out.println("<<<\t"+rset.getString("name"));
	        }


	    }
 

}
