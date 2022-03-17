package com.nttdata.casestudy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.nttdata.casestudy.model.User;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class LogoutController implements HttpRequestHandler {

	private static final Logger log = LogManager.getLogger(LogoutController.class);

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
		log.info("Logging our user: " + user.getName() );

		session.invalidate();

		RequestDispatcher rd= request.getRequestDispatcher("index.html");	
		rd.forward(request, response);
	}
}