package com.nttdata.casestudy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.caseStudy.dao.OrderDAO;
import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.model.OrderView;
import com.nttdata.casestudy.model.User;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class CustomerOrderView implements HttpRequestHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		HttpSession session =request.getSession();

		User user =(User) session.getAttribute("user");

		OrderDAO dao = new OrderDAO(ConnectionHolder.getInstance().getDataSource());
		List<OrderView> orderView= dao.fetchOrdersForCustomer(user.getUserId());

		RequestDispatcher rd=request.getRequestDispatcher("CustomerOrderView.jsp");
		request.setAttribute("orderView", orderView);
		rd.forward(request, response);

	}

}
