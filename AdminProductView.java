package com.nttdata.casestudy.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caseStudy.dao.ProductDAO;
import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.model.Product;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class AdminProductView implements HttpRequestHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ProductDAO dao = new ProductDAO(ConnectionHolder.getInstance().getDataSource());
		List<Product> products=dao.getAllProducts();

		RequestDispatcher rd = request.getRequestDispatcher("Product.jsp");
		System.out.println(products);
		request.setAttribute("products", products);
		rd.forward(request,response);	

	}

}
