package com.nttdata.casestudy.model;

import java.sql.Date;

public class OrderView {

	private int orderID;
	private String emailID;
	private String userName;
	private String address;
	private int quantity;
	private String productName;
	private Date orderDate;
	private int orderPrice;

	public OrderView(int orderID, String emailID, String userName, String address, int quantity, String productName,
			Date orderDate, int orderPrice) {
		super();
		this.orderID = orderID;
		this.emailID = emailID;
		this.userName = userName;
		this.address = address;
		this.quantity = quantity;
		this.productName = productName;
		this.orderDate=orderDate;
		this.orderPrice=orderPrice;
	}

	public OrderView()
	{
	}

	//setters and getters
	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getEmailID() {
		return emailID;
	}

	public void setEmailID(String emailID) {
		this.emailID = emailID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Date getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public int getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(int orderPrice) {
		this.orderPrice = orderPrice;
	}

	@Override
	public String toString() {
		return "OrderView [orderID=" + orderID + ", emailID=" + emailID + ", userName=" + userName + ", address="
				+ address + ", quantity=" + quantity + ", productName=" + productName + ", orderDate=" + orderDate
				+ ", orderPrice=" + orderPrice + "]";
	}



}
