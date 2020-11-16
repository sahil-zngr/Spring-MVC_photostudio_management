package com.photostudio.models;

import java.sql.Date;
import java.sql.Time;

public class orders {

	private int order_id;
	private int customer_id;
	private int employee_id;
	
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date orderdate;
	
	//@DateTimeFormat(pattern = "hh:mm")
	private Time ordertime;
	
	private int total_price;

	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getEmployee_id() {
		return employee_id;
	}

	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}

	public Date getOrderdate() {
		return orderdate;
	}

	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}

	public Time getOrdertime() {
		return ordertime;
	}

	public void setOrdertime(Time ordertime) {
		this.ordertime = ordertime;
	}

	public int getTotal_price() {
		return total_price;
	}

	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}


	
	
	
}
