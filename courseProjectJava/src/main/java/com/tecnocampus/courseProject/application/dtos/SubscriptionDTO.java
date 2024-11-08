package com.tecnocampus.courseProject.application.dtos;

import java.util.Calendar;
import java.util.UUID;

public class SubscriptionDTO {
	
	private String id;
	private int amount;
	private double price;
	private Calendar initialDate;
	private ProductDTO product;
	
	public SubscriptionDTO() {
		id=UUID.randomUUID().toString();
		initialDate = Calendar.getInstance(); 
		initialDate.setTime(new java.util.Date()); 
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setInitialDate(Calendar initialDate) {
		this.initialDate = initialDate;
	}

	public ProductDTO getProduct() {
		return product;
	}

	public void setProduct(ProductDTO product) {
		this.product = product;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public Calendar getInitialDate() {
		return initialDate;
	}

}
