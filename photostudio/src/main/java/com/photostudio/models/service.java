package com.photostudio.models;

import java.sql.Date;
import java.time.LocalTime;

public class service {

	private int service_id;
	private int order_id;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date servicedate;
	//@DateTimeFormat(pattern = "hh:mm:ss")
	private String servicetime;
	private int price;
	public int getService_id() {
		return service_id;
	}
	public void setService_id(int service_id) {
		this.service_id = service_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public Date getServicedate() {
		return servicedate;
	}
	public void setServicedate(Date servicedate) {
		this.servicedate = servicedate;
	}
	public String getServicetime() {
		return servicetime;
	}
	public void setServicetime(String servicetime) {
		this.servicetime = servicetime;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
}
