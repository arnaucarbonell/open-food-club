package com.tecnocampus.courseProject.application.dtos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class OrderDTO {
	
	private String id;
	private Calendar date;
	private String state;
	private List<SubscriptionDTO> subscriptions;
	
	public OrderDTO() {
		id=UUID.randomUUID().toString();
		date = Calendar.getInstance(); 
		date.setTime(new java.util.Date()); 
		date.add(Calendar.DAY_OF_MONTH, 7);
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getDate() {
		return date;
	}

	public String  getState() {
		return state;
	}

	public void setState(String  state) {
		this.state = state;
	}

	public List<SubscriptionDTO> getSubscriptions() {
		if(this.subscriptions==null)this.subscriptions=new ArrayList<>();
		return subscriptions;
	}
	
	public SubscriptionDTO getSubscription(String id) throws Exception {
		for(int i=0;i<subscriptions.size();i++) {
			if(subscriptions.get(i).getId().equals(id)) {
				return subscriptions.get(i);
			}
		}
		throw new Exception("sub not found");
	}

	public void setSubscriptions(List<SubscriptionDTO> subscriptions) {
		this.subscriptions = subscriptions;
	}
	
	public void addSubscription(SubscriptionDTO subscription) {
		if(this.subscriptions==null)this.subscriptions=new ArrayList<>();
		this.subscriptions.add(subscription);
	}

	public String getId() {
		return id;
	}

}
