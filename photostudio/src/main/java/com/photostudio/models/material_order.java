package com.photostudio.models;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

public class material_order {

	private int morder_id;
	private int material_id;
	private int employee_id;
	private int supplier_id;
	//@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date odate;
	//@DateTimeFormat(pattern = "hh:mm:ss")
	private Time otime;
	private int quantity;
	private int price;
	public int getMorder_id() {
		return morder_id;
	}
	public void setMorder_id(int morder_id) {
		this.morder_id = morder_id;
	}
	public int getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(int material_id) {
		this.material_id = material_id;
	}
	public int getEmployee_id() {
		return employee_id;
	}
	public void setEmployee_id(int employee_id) {
		this.employee_id = employee_id;
	}
	public int getSupplier_id() {
		return supplier_id;
	}
	public void setSupplier_id(int supplier_id) {
		this.supplier_id = supplier_id;
	}
	public Date getOdate() {
		return odate;
	}
	public void setOdate(Date odate) {
		this.odate = odate;
	}
	public Time getOtime() {
		return otime;
	}
	public void setOtime(Time otime) {
		this.otime = otime;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
