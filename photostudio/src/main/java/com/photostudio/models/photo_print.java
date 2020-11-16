package com.photostudio.models;

public class photo_print {

	private int print_id;
	private int order_id;
	private String print_size;
	private int price;
	
	public int getPrint_id() {
		return print_id;
	}
	public void setPrint_id(int print_id) {
		this.print_id = print_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getPrint_size() {
		return print_size;
	}
	public void setPrint_size(String print_size) {
		this.print_size = print_size;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
}
