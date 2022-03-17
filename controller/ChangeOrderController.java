package com.nttdata.casestudy.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.caseStudy.dao.OrderDAO;
import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.model.User;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class ChangeOrderController implements HttpRequestHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Integer productID =Integer.parseInt(request.getParameter("productId"));
		Integer quant=Integer.parseInt(request.getParameter("quantity"));

		HttpSession session=request.getSession();

		User user= (User)session.getAttribute("user");

		Integer orderID = (Integer) session.getAttribute("orderID");
		session.removeAttribute("orderID");


		OrderDAO dao = new OrderDAO(ConnectionHolder.getInstance().getDataSource());
		dao.modifyOrder(orderID, productID, quant);

		if("customer".equals(user.getRole()))
		{
			response.sendRedirect("CustomerView.jsp");
		}
		else if("admin".equals(user.getRole()))
		{
			response.sendRedirect("AdminView.jsp");
		}
	}

}
