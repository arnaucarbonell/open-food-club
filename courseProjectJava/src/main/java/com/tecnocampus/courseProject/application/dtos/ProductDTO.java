package com.tecnocampus.courseProject.application.dtos;

public class ProductDTO {
	
	private String id;
	private String name;
	private double price;
	private String provider;
	private String unidadDeMedida;
	private String iva;
	private String category;
	private int num_period;
	private String periodicityType;
	private String img;
	
	public ProductDTO() {
	}

	public void setNumPeriod(int num_period) {
		this.num_period = num_period;
	}

	public int getNumPeriod() {
		return num_period;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setImg(String img) {
		this.img = img;
	}

	public String getImg() {
		return img;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getPeriodicityType() {
		return periodicityType;
	}

	public void setPeriodicityType(String periodicityType) {
		this.periodicityType = periodicityType;
	}

	public String getUnidadDeMedida() {
		return unidadDeMedida;
	}

	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

}
