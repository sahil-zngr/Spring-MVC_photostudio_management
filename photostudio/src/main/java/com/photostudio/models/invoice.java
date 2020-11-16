package com.photostudio.models;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

public class invoice {

	private int invoice_id;
	private int order_id;
	private int customer_id;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private java.sql.Date pdate;
	//@DateTimeFormat(pattern = "hh:mm:ss")
	private Time ptime;
	private int amount;
	private String status;
	public int getInvoice_id() {
		return invoice_id;
	}
	public void setInvoice_id(int invoice_id) {
		this.invoice_id = invoice_id;
	}
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
	public java.sql.Date getPdate() {
		return pdate;
	}
	public void setPdate(java.sql.Date pdate) {
		this.pdate = pdate;
	}
	public Time getPtime() {
		return ptime;
	}
	public void setPtime(Time ptime) {
		this.ptime = ptime;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
