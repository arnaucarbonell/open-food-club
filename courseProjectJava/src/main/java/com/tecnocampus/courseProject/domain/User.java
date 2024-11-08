package com.tecnocampus.courseProject.domain;

import java.util.ArrayList;
import java.util.List;

public class User {
	
	private String id;
	private String name;
	private String secondName;
	private String email;
	private List<Order> orders;
	
	public User() {}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public List<Order> getOrders() {
		if(orders==null)return new ArrayList<Order>();
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}
	
	public void addOrder(Order order) {
		if(this.orders==null)this.orders=new ArrayList<>();
		this.orders.add(order);
	}
	
	public Order getOrderWithState(String state) {
		for(int i=0;i<orders.size();i++) {
			if(orders.get(i).getState().equals(state)) {
				return orders.get(i);
			}
		}
		return null;
	}

}
