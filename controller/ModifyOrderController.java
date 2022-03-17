package com.nttdata.casestudy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.caseStudy.dao.OrderDAO;
import com.caseStudy.dao.ProductDAO;
import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.model.Product;
import com.nttdata.casestudy.model.User;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class ModifyOrderController implements HttpRequestHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Integer orderID=Integer.parseInt(request.getParameter("orderId"));
		String operation= request.getParameter("operation");

		HttpSession session =request.getSession();
		User user =(User) session.getAttribute("user");
		System.out.println(orderID);
		System.out.println(operation);

		if(orderID==null || operation==null)
		{
			response.sendRedirect("Product.jsp");
		}

		if("customer".equals(user.getRole()))
		{
			if("delete".equals(operation))
			{
				OrderDAO orderDao = new OrderDAO(ConnectionHolder.getInstance().getDataSource());
				orderDao.removeCustomerOrder(user.getUserId(), orderID);
				response.sendRedirect("CustomerView.jsp");
			}
			else if("modify".equals(operation))
			{
				ProductDAO dao = new ProductDAO(ConnectionHolder.getInstance().getDataSource());
				List<Product> products=dao.getAllProducts();
				RequestDispatcher rd= request.getRequestDispatcher("ModifyOrder.jsp");
				request.setAttribute("products", products);
				session =request.getSession();
				session.setAttribute("orderID", orderID);
				rd.forward(request, response);
			}
		}
		else if("admin".equals(user.getRole()))
		{
			if("delete".equals(operation))
			{
				OrderDAO orderDao = new OrderDAO(ConnectionHolder.getInstance().getDataSource());
				orderDao.removeAdminOrder(orderID);
				response.sendRedirect("AdminView.jsp");
			}
			else if("modify".equals(operation))
			{
				ProductDAO dao = new ProductDAO(ConnectionHolder.getInstance().getDataSource());
				List<Product> products=dao.getAllProducts();
				RequestDispatcher rd= request.getRequestDispatcher("ModifyOrder.jsp");
				request.setAttribute("products", products);
				session =request.getSession();
				session.setAttribute("orderID", orderID);
				rd.forward(request, response);
			}
		}
	}

}
