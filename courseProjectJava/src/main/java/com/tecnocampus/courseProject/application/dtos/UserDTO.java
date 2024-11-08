package com.tecnocampus.courseProject.application.dtos;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {
	
	private String id;
	private String name;
	private String secondName;
	private String email;
	private List<OrderDTO> orders;
	
	public UserDTO() {
		orders=new ArrayList<>();
	}

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

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}
	
	public void addOrder(OrderDTO order) {
		if(this.orders==null)this.orders=new ArrayList<>();
		this.orders.add(order);
	}
	
	public OrderDTO getOrderWithState(String state) {
		for(int i=0;i<orders.size();i++) {
			if(orders.get(i).getState().equals(state)) {
				return orders.get(i);
			}
		}
		return null;
	}

}
