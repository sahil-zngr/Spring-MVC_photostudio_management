package com.photostudio.models;

public class material_sold {

	private int materialsold_id;
	private int material_id;
	private int order_id;
	private String name;
	private int price;
	public int getMaterialsold_id() {
		return materialsold_id;
	}
	public void setMaterialsold_id(int materialsold_id) {
		this.materialsold_id = materialsold_id;
	}
	public int getMaterial_id() {
		return material_id;
	}
	public void setMaterial_id(int material_id) {
		this.material_id = material_id;
	}
	public int getOrder_id() {
		return order_id;
	}
	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	
	
	
	
}
