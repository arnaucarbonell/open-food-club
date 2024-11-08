package com.tecnocampus.courseProject.domain;

import java.util.Calendar;
import java.util.List;

public class Order {
	
	private String id;
	private Calendar date;
	private String state;
	private List<Subscription> subscriptions;
	
	public Order() {}

	public void setId(String id) {
		this.id = id;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public Calendar getDate() {
		return date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}
	
	public Subscription getSubscription(String id) throws Exception {
		for(int i=0;i<subscriptions.size();i++) {
			if(subscriptions.get(i).getId().equals(id)) {
				return subscriptions.get(i);
			}
		}
		throw new Exception("sub not found");
	}

	public void setSubscriptions(List<Subscription> subscriptions) {
		this.subscriptions = subscriptions;
	}
	
	public void addSubscription(Subscription subscription) {
		this.subscriptions.add(subscription);
	}

	public String getId() {
		return id;
	}

}
