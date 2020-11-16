package com.photostudio.models;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class sendemail {

	private int emailkey;
	private int order_id;
	private String customer_email;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.sql.Date sdate;
	//@DateTimeFormat(pattern = "hh:mm:ss")
	private Time stime;
	private String status;
	public int getEmailkey() {
		return emailkey;
	}
	public void setEmailkey(int emailkey) {
		this.emailkey = emailkey;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getCustomer_email() {
		return customer_email;
	}
	public void setCustomer_email(String customer_email) {
		this.customer_email = customer_email;
	}
	public java.sql.Date getSdate() {
		return sdate;
	}
	public void setSdate(java.sql.Date sdate) {
		this.sdate = sdate;
	}
	public Time getStime() {
		return stime;
	}
	public void setStime(Time stime) {
		this.stime = stime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
