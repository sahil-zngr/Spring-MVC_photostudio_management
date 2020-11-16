package com.photostudio.models;

import java.sql.Date;
import java.sql.Time;

import org.springframework.format.annotation.DateTimeFormat;

public class outdoororder {

	private int outdoororder_id;
	private int order_id;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date sdate;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date edate;
	//@DateTimeFormat(pattern = "hh:mm")
	private String stime;
	//@DateTimeFormat(pattern = "hh:mm")
	private String etime;
	private String ordertype;
	private String service_req;
	private int price;
	public int getOutdoororder_id() {
		return outdoororder_id;
	}
	public void setOutdoororder_id(int outdoororder_id) {
		this.outdoororder_id = outdoororder_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public Date getSdate() {
		return sdate;
	}
	public void setSdate(Date sdate) {
		this.sdate = sdate;
	}
	public Date getEdate() {
		return edate;
	}
	public void setEdate(Date edate) {
		this.edate = edate;
	}
	public String getStime() {
		return stime;
	}
	public void setStime(String stime) {
		this.stime = stime;
	}
	public String getEtime() {
		return etime;
	}
	public void setEtime(String etime) {
		this.etime = etime;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getService_req() {
		return service_req;
	}
	public void setService_req(String service_req) {
		this.service_req = service_req;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
	
}
