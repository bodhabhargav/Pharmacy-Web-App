package com.caseStudy.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.nttdata.casestudy.dbfw.ResultMapper;
import com.nttdata.casestudy.model.Order;
import com.nttdata.casestudy.model.OrderView;
import com.nttdata.casestudy.model.Product;
import com.nttdata.casestudy.model.User;




public class SQLMapper
{

	public static final String GET_ALL_PRODUCTS_By_CATEGORY= "select * from product where category_id=?"; 

	public static final String GET_PRICE_BY_PROD_ID= "select product_price from product where product_id=?";

	public static final String GET_ALL_PRODUCTS = "select * from product";


	public static final ResultMapper getAllProductsMapper = new ResultMapper()
	{

		@Override
		public Object mapRow(ResultSet resultSet) throws SQLException
		{

			Product product = new Product();
			product.setId(resultSet.getInt(1));
			product.setName(resultSet.getString(2));
			product.setPrice(resultSet.getInt(3));
			product.setCategoryId(resultSet.getInt(4));			    
			return product;
		}
		//mapRow

	};

	//Anonymous class
	public static final ResultMapper getPriceByProdIdMapper = new ResultMapper()
	{

		@Override
		public Object mapRow(ResultSet resultSet) throws SQLException
		{

			return resultSet.getInt(1);
		}
		//mapRow

	};

	public static final String DISPLAY_ALL_ORDERS ="select orders.order_id, user.email_id,"
			+ " user.name, user.address,"
			+ " line_items.quantity,"
			+ " product.product_name,"
			+ " orders.order_date,"
			+ " orders.order_price"
			+ " from orders"
			+ " inner join user on orders.user_id=user.user_id"
			+ " inner join line_items on orders.order_id = line_items.order_id"
			+ " inner join product on  product.product_id =  line_items.product_id"; 


	public static final ResultMapper getAllOrdersMapper = new ResultMapper()
	{

		@Override
		public Object mapRow(ResultSet rs) throws SQLException
		{

			OrderView obj=new OrderView();
			obj.setOrderID(rs.getInt(1));
			obj.setEmailID(rs.getString(2));
			obj.setUserName(rs.getString(3));
			obj.setAddress(rs.getString(4));
			obj.setQuantity(rs.getInt(5));
			obj.setProductName(rs.getString(6));
			obj.setOrderDate(rs.getDate(7));
			obj.setOrderPrice(rs.getInt(8));
			return obj;
		}
		//mapRow

	};

	public static final String DISPLAY_ALL_ORDERS_BY_USERID=
			DISPLAY_ALL_ORDERS + " where user.user_id = ?";




	//	public static final String MAX_ORDER_ID="select max(order_id) as order_id from orders where user_id=?";
	//	
	//	public static final ResultMapper getMaxOrderID =new ResultMapper();
	//	{
	//		@Override
	//		m
	//		
	//	}


	public static final String INSERT_INTO_ORDER=
			"insert into orders (user_id, order_price, order_date) values(?, ?, ?)";


	public static final String deleteLineItemsUsingOrderId="delete from line_items where order_id=?";

	public static final String deleteOrdersUsingOrderId="delete from orders where order_id=?";


	public static final String getMaxOrderID=
			"select max(order_id) as order_id from orders where user_id=?";

	public static final ResultMapper retrieveMaxOrderID = new ResultMapper()
	{

		@Override
		public Object mapRow(ResultSet resultSet) throws SQLException
		{

			return resultSet.getString(1);
		}
		//mapRow

	};

	public static final String insertIntoLineItems=
			"insert into line_items (product_id,quantity,order_id) values(?, ?, ?)";

	public static String registerUser="insert into user values(?,?,?,?,?,?,?,?,?)";


	public static final String get_OrdersUsingUserId=
			"select * from orders where user_id=?";

	public static final ResultMapper getOrdersforUserID = new ResultMapper()
	{

		@Override
		public Object mapRow(ResultSet rs) throws SQLException
		{


			Order order=new Order();
			order.setOrder_id(rs.getInt(1));
			order.setUser_id(rs.getString(2)); 
			order.setOrder_price(rs.getInt(3));
			order.setOrder_date(rs.getDate(4));

			return order;
		}


	};

	public static final String GET_USER_PROFILE =
			"select * from user where user_id=? and user_password = ?";


	public static final ResultMapper getUserProfile = new ResultMapper()
	{

		@Override
		public Object mapRow(ResultSet rs) throws SQLException{
			User user=new User();
			user.setUserId(rs.getString("user_id"));
			user.setPassword(rs.getString("user_password"));
			user.setName(rs.getString("name"));
			user.setAddress(rs.getString("address"));
			user.setDob(rs.getDate("dob"));
			user.setEmail(rs.getString("email_id"));
			user.setPhoneNum(rs.getString("contact_number"));
			user.setPinCode(rs.getInt("pincode"));
			user.setRole(rs.getString("role"));
			user.setAuthSuccessful(true);
			return user;
		}
	};

	public static final String updateLineItems="update line_items"
			+ " set product_id=? , quantity=?  where order_id=?";

	public static final String getProductPrice="select product_price from product"
			+ " where product_id=?";

	public static final ResultMapper getProductValues = new ResultMapper()
	{

		@Override
		public Object mapRow(ResultSet rs) throws SQLException{
			Product product=new Product();
			product.setPrice(rs.getInt(1));
			return product;
		}
	};

	public static final String updateOrdersUsingOrderID="update orders"
			+ " set order_price=?"
			+ " where order_id=?";
}


