package com.nttdata.casestudy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.caseStudy.dao.UserDAO;
import com.caseStudy.dbcon.ConnectionHolder;
import com.nttdata.casestudy.model.User;
import com.nttdata.casestudy.mvc.HttpRequestHandler;

public class RegisterController implements HttpRequestHandler {
	private static final Logger log = LogManager.getLogger(RegisterController.class);
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String userID=request.getParameter("userId");
		log.info("Registring user:" + userID);
		System.out.println(userID);
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String emailId=request.getParameter("emailId");
		String address=request.getParameter("address");
		Integer pinCode =Integer.parseInt(request.getParameter("pinCode"));
		String dob=request.getParameter("dob");

		String phoneNumber=request.getParameter("phoneNumber");
		String role=request.getParameter("role");

		User user=new User();

		user.setUserId(userID);
		user.setPassword(pwd);
		user.setName(name);
		user.setEmail(emailId);
		user.setAddress(address);
		user.setPinCode(pinCode);
		user.setDob(Date.valueOf(dob));
		user.setPhoneNum(phoneNumber);
		user.setRole(role);

		System.out.println(user);

		UserDAO userDao = new UserDAO(ConnectionHolder.getInstance().getDataSource());
		userDao.registerUser(user);

		RequestDispatcher rd = request.getRequestDispatcher("Login.jsp");	
		rd.forward(request, response);


	}

}
