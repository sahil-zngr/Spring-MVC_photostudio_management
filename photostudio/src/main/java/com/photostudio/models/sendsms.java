package com.photostudio.models;

import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class sendsms {

	private int smskey;
	private int order_id;
	private String customer_contact;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sdate;
	@DateTimeFormat(pattern = "hh:mm:ss")
	private LocalTime stime;
	private String status;
	public int getSmskey() {
		return smskey;
	}
	public void setSmskey(int smskey) {
		this.smskey = smskey;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getCustomer_contact() {
		return customer_contact;
	}
	public void setCustomer_contact(String customer_contact) {
		this.customer_contact = customer_contact;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public LocalTime getStime() {
		return stime;
	}
	public void setStime(LocalTime stime) {
		this.stime = stime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
