package com.nttdata.casestudy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caseStudy.dao.OrderDAO;
import com.caseStudy.dao.ProductDAO;
import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.model.OrderView;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class AllOrderView implements HttpRequestHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		OrderDAO dao = new OrderDAO(ConnectionHolder.getInstance().getDataSource());
		List<OrderView> orderView= dao.displayAllOrders();

		RequestDispatcher rd=request.getRequestDispatcher("CustomerOrderView.jsp");
		request.setAttribute("orderView", orderView);
		rd.forward(request, response);

	}

}
