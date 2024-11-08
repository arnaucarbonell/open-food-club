package com.tecnocampus.courseProject.domain;

import java.util.Calendar;

public class Subscription {
	
	private String id;
	private Product product;
	private int amount;
	private double price;
	private Calendar initialDate;
	
	public Subscription() {}

	public void setId(String id) {
		this.id = id;
	}

	public void setInitialDate(Calendar initialDate) {
		this.initialDate = initialDate;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getId() {
		return id;
	}

	public Calendar getInitialDate() {
		return initialDate;
	}
	
}
