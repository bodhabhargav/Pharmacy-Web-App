package com.nttdata.casestudy.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.caseStudy.dao.LineItemsDAO;
import com.caseStudy.dao.OrderDAO;
import com.caseStudy.dao.ProductDAO;
import com.caseStudy.dao.UserDAO;
import com.mysql.cj.jdbc.MysqlDataSource;
import com.nttdata.casestudy.model.LineItem;
import com.nttdata.casestudy.model.Order;
import com.nttdata.casestudy.model.OrderView;
import com.nttdata.casestudy.model.User;

public class OrderTestDAO {

	static int orderID;

	private static DataSource getDataSource() {	        		        	
		MysqlDataSource ds1 = new MysqlDataSource();
		ds1.setURL("jdbc:mysql://localhost:3306/case_study");
		ds1.setUser("root");
		ds1.setPassword("1234");	            
		return ds1;
	}


	@BeforeClass
	public static void setUp() throws Exception {

		DataSource ds = getDataSource();
		Connection con1=ds.getConnection();

		String sql1 ="delete from line_items where quantity=100";
		
		String sql2 ="delete from line_items where quantity=500";

		String sql3="delete from orders where user_id=\"TestUser123\"";

		String sql4="delete from user where user_id=\"TestUser123\"";

		Statement stmt = con1.createStatement();

		// Set auto-commit to false
		con1.setAutoCommit(false);

		stmt.addBatch(sql1);
		stmt.addBatch(sql2);
		stmt.addBatch(sql3);
		stmt.addBatch(sql4);

		stmt.executeBatch();

		con1.commit();
		User user= OrderTestDAO.userSetup();

		UserDAO userDao = new UserDAO(ds);
		userDao.registerUser(user);

		OrderTestDAO.orderSetup();
		
		String sql5="select order_id from orders where user_id=\"TestUser123\"";

		PreparedStatement statement =con1.prepareStatement(sql5);
		
		ResultSet rs=statement.executeQuery();


		if(rs.next())
		{
			orderID=rs.getInt(1);
		}




	}

	public static User userSetup()
	{

		String str="1992-03-31";  
		Date date=Date.valueOf(str);//converting string into sql date  
		//System.out.println(date); 

		User user =new User("TestUser123", "1234", "Test User", "testuser@gmail.com",
				"London, UK", date , "1234567890", 700010,
				"customer");

		return user;

	}
	
	public static void orderSetup()
	{
		Order order=new Order();
		LineItem item = new LineItem();



		order.setUser_id("TestUser123");

		//Setting the product id to the product id present inside the Line Items object.
		//System.out.println("Enter the Id for the product which you want to order: ");
		item.setProductID(10);


		//Asking the user to enter the number of products of one kind
		//System.out.println("Enter the quantity of the products you want to order: ");
		int quant=100;
		item.setQuantity(quant);


		//setting order price
		ProductDAO dao = new ProductDAO(getDataSource());
		int price = quant*dao.getPrice(10);
		order.setOrder_price(price);

		//setting current date as Orderdate
		long millis=System.currentTimeMillis();
		order.setOrder_date(new java.sql.Date(millis));

		OrderDAO orderDao = new OrderDAO(getDataSource());   
		orderDao.createOrder(order);	

		LineItemsDAO lineItemsDao =new LineItemsDAO(getDataSource());
		lineItemsDao.createLineItem(item, "TestUser123");
	}


	@Test
	public void testOrderViewForOneCustomer() {

		OrderDAO orderDao=new OrderDAO(getDataSource());
		List<OrderView> views= orderDao.fetchOrdersForCustomer("TestUser123");
		System.out.println("Orders: " + views);
		assertTrue(views.size()==1);		
	} 

	@Test
	public void testOrderViewforAllCustomers(){
		OrderDAO orderDao = new OrderDAO(getDataSource());
		List<OrderView> views =orderDao.displayAllOrders();
		assertTrue(views.size()>10);
	}
	
	@Test
	public void testModifyOrder() throws Exception
	{
		OrderDAO orderDao =new OrderDAO(getDataSource());
		
		System.out.println(orderID);
		orderDao.modifyOrder(orderID, 1, 500);
		
		List<OrderView> view = orderDao.fetchOrdersForCustomer("TestUser123");
		
		System.out.println(view);
		
		OrderView view1=view.get(0);
		
		System.out.println(view1);
		
		assertTrue(view1.getOrderPrice()==10000);
		
	}
	
	@AfterClass
	public static void testRemoveCustomerOrder() throws Exception
	{
		OrderDAO orderDao =new OrderDAO(getDataSource());
		System.out.println(orderID);
		//have to enter the orderID manually for 'TestUser123'
		orderDao.removeCustomerOrder("TestUser123", orderID);

		List<OrderView> views =orderDao.fetchOrdersForCustomer("TestUser123");
		

		assertEquals(views.size(),0);
	}

	@Test
	public void testAuthenticate()
	{
		UserDAO userDao =new UserDAO(getDataSource());
		User user=userDao.authenticate("TestUser123", "1234");
		
		assertTrue("testuser@gmail.com".equals(user.getEmail()));
	}

}
