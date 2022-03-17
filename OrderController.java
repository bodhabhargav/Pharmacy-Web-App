package com.nttdata.casestudy.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.caseStudy.dao.LineItemsDAO;
import com.caseStudy.dao.OrderDAO;
import com.caseStudy.dao.ProductDAO;
import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.model.LineItem;
import com.nttdata.casestudy.model.Order;
import com.nttdata.casestudy.model.User;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class OrderController implements HttpRequestHandler {



	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//		PrintWriter out=response.getWriter();

		Integer productID =Integer.parseInt(request.getParameter("productId"));
		Integer quant=Integer.parseInt(request.getParameter("quantity"));

		HttpSession session=request.getSession();

		User user= (User)session.getAttribute("user");

		Order order=new Order();
		LineItem item = new LineItem();

		System.out.println(user);


		order.setUser_id(user.getUserId());

		//Setting the product id to the product id present inside the Line Items object.
		item.setProductID(productID);


		//Asking the user to enter the number of products of one kind
		item.setQuantity(quant);


		//setting order price
		ProductDAO dao = new ProductDAO(ConnectionHolder.getInstance().getDataSource());
		int price = quant * dao.getPrice(productID);
		order.setOrder_price(price);

		//setting current date as Orderdate
		long millis=System.currentTimeMillis();
		order.setOrder_date(new java.sql.Date(millis));

		OrderDAO orderDao = new OrderDAO(ConnectionHolder.getInstance().getDataSource());
		orderDao.createOrder(order);	
		
		LineItemsDAO lineItemsDao =new LineItemsDAO(ConnectionHolder.getInstance().getDataSource());
		lineItemsDao.createLineItem(item, user.getUserId());


		RequestDispatcher rd = request.getRequestDispatcher("customerOrderView.do");
		rd.forward(request, response);
		//		out.println("<html>");
		//		out.println("Order placed successfully");
		//		out.println("Your order price is "+ order.getOrder_price());
		//		out.println("</html>");

	}

}
