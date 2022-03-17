package com.nttdata.casestudy.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.caseStudy.dao.UserDAO;
import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.model.User;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class LoginController implements HttpRequestHandler {

	private static final Logger log = LogManager.getLogger(LoginController.class);
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId=request.getParameter("userId");
		String pwd=request.getParameter("pwd");

		UserDAO userDao = new UserDAO(ConnectionHolder.getInstance().getDataSource());
		User user = userDao.authenticate(userId, pwd);

		System.out.println("User:" + user);
		log.info("User: " + user);

		if (user != null && user.isAuthSuccessful()) {
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
		}

		RequestDispatcher rd = null;
		if (user != null && "customer".equals(user.getRole())){
			rd = request.getRequestDispatcher("CustomerView.jsp");			
		}else if (user != null && "admin".equals(user.getRole())){
			rd = request.getRequestDispatcher("AdminView.jsp");			
		}else {
			request.setAttribute("userId", userId);
			request.setAttribute("pwd", pwd);
			request.setAttribute("Msg", "Invalid credentials. Please enter correct details");
			rd = request.getRequestDispatcher("Login.jsp");
		}
		rd.forward(request, response);

	}

}
