package com.caseStudy.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.sql.DataSource;

import com.nttdata.casestudy.dbfw.DBConnectionException;
import com.nttdata.casestudy.dbfw.DBFWException;
import com.nttdata.casestudy.dbfw.DBHelper;
import com.nttdata.casestudy.dbfw.ParamMapper;
import com.nttdata.casestudy.model.Order;
import com.nttdata.casestudy.model.OrderView;
import com.nttdata.casestudy.model.Product;

public class OrderDAO {

	static  DataSource ds = null;

	public OrderDAO(DataSource ds) {
		this.ds = ds;		
	}
	
	//constructor
	public OrderDAO()
	{
		
	}

	//create new order for customer
	public void createOrder(Order order)
	{

		try(Connection con1=ds.getConnection();)
		{
			final ParamMapper INSERTORDER=new ParamMapper()
			{
				public void mapParam(PreparedStatement preStmt) throws SQLException 
				{

					preStmt.setString(1, order.getUser_id());
					preStmt.setInt(2,order.getOrder_price());
					preStmt.setDate(3, order.getOrder_date());			
				}
			};


			//result=DBHelper.executeUpdate( con,SQLMapper.INSERTCOUNTRY,INSERTPCOUNTRY);

			DBHelper.executeUpdate(con1,SQLMapper.INSERT_INTO_ORDER,INSERTORDER);

		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	//display orders for a particular customer by passing the customers user ID
	public List<OrderView> fetchOrdersForCustomer(String userId)
	{
		List<OrderView> li=new ArrayList<OrderView>();
		try(Connection con1=ds.getConnection();)
		{
			final ParamMapper orderParamMapper=new ParamMapper()
			{

				public void mapParam(PreparedStatement preStmt) throws SQLException {
					preStmt.setString(1,userId);
				}

			};

			li=DBHelper.executeSelect(con1,
					SQLMapper.DISPLAY_ALL_ORDERS_BY_USERID, 
					SQLMapper.getAllOrdersMapper,
					orderParamMapper);

		}
		catch (DBFWException e) {
			throw new DBFWException("Exception in DBFW : " + e);
		}catch (SQLException se) {
			throw new DBConnectionException("Unable to connect to db"+se);
		}

		return li;
	}

	/*
	 * Delete order by passing the userID as an argument and then select the order
	 *   so that you can delete the order for the particular OrderId passed.
	 */
	public void removeCustomerOrder(String userID , int delOrderID)
	{
		
		try(Connection con1=ds.getConnection();)
		{
			final ParamMapper orderParamMapper=new ParamMapper()
			{

				public void mapParam(PreparedStatement statement) throws SQLException {
					statement.setString(1,userID);
				}

			};

			List<Order> order=new ArrayList<Order>();

			order=DBHelper.executeSelect(con1, SQLMapper.get_OrdersUsingUserId
					, SQLMapper.getOrdersforUserID
					,orderParamMapper);


			//deleting all the orders for a particular order id
			OrderDAO orderDao=new OrderDAO();
			orderDao.removeAdminOrder(delOrderID);
		}
		catch(SQLException e)
		{
			System.out.println(e);
		}
	}

	//done
	public List<OrderView> displayAllOrders()
	{
		List<OrderView> li=new ArrayList<OrderView>();
		try(Connection con1=ds.getConnection();)
		{
			li=DBHelper.executeSelect(con1,
					SQLMapper.DISPLAY_ALL_ORDERS, 
					SQLMapper.getAllOrdersMapper);

			//OrderView obj=null;
		}
		catch (SQLException ex)
		{
			System.out.println(ex);
		}

		return li;
	}

	public void removeAdminOrder(int removeOrder)
	{
		try(Connection con1=ds.getConnection();)
		{
			final ParamMapper DelLineItemsUsingOrderID=new ParamMapper()
			{
				public void mapParam(PreparedStatement statement)	throws SQLException	
				{
					statement.setInt(1, removeOrder);

				}
			};

			DBHelper.executeUpdate(con1,
					SQLMapper.
					deleteLineItemsUsingOrderId, 
					DelLineItemsUsingOrderID);

			final ParamMapper DelOrderUsingOrderID=new ParamMapper()
			{
				public void mapParam(PreparedStatement preStmt) throws SQLException 
				{

					preStmt.setInt(1, removeOrder);

					int rows=preStmt.executeUpdate();

					if(rows>0)
						System.out.println("Order deleted successfully");
					else
						System.out.println("Order not deleted.");

				}
			};

			DBHelper.executeUpdate(con1,
					SQLMapper.
					deleteOrdersUsingOrderId, 
					DelOrderUsingOrderID);

		} 
		catch (SQLException e) 
		{
			System.out.println(e);
		}
	}

	public void modifyOrder(int orderID, int productId, int quantity)
	{
		try(Connection con1=ds.getConnection();)
		{
			// Delete Order 
			final ParamMapper DelOrderUsingOrderID=new ParamMapper()
			{
				public void mapParam(PreparedStatement statement) throws SQLException 
				{					
					statement.setInt(1, productId);
					statement.setInt(2, quantity);
					statement.setInt(3, orderID);

				}
			};

			DBHelper.executeUpdate(con1, SQLMapper.updateLineItems, DelOrderUsingOrderID);

			//get Price for the Product
			final ParamMapper selectPriceByProductId = new ParamMapper()
			{

				public void mapParam(PreparedStatement statement) throws SQLException 
				{					
					statement.setInt(1, productId);
				}
			};

			List<Product> li=DBHelper.executeSelect(con1,
					SQLMapper.getProductPrice, SQLMapper.getProductValues, selectPriceByProductId);

			final int productPrice =  li.get(0).getPrice();

			// Update Order
			final ParamMapper UpdateOrders=new ParamMapper()
			{
				public void mapParam(PreparedStatement st) throws SQLException 
				{										
					st.setInt(1, quantity*productPrice);
					st.setInt(2, orderID);
				}
			};

			DBHelper.executeUpdate(con1, SQLMapper.updateOrdersUsingOrderID, UpdateOrders);



		}
		catch(SQLException ex)
		{
			System.out.println(ex);
		}
	}
}
