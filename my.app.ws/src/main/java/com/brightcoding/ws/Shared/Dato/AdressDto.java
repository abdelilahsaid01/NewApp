package com.brightcoding.ws.Shared.Dato;

public class AdressDto {
	private long id;
	private String addressId;
	private String city;
	private String country;
	private String street;
	private String postal;
	private String type;
	private UserDto user;	//Cette adresses est appartient à un utilisateur
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCounntry() {
		return country;
	}
	public void setCounntry(String counntry) {
		this.country = counntry;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getPostal() {
		return postal;
	}
	public void setPostal(String postal) {
		this.postal = postal;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public UserDto getUser() {
		return user;
	}
	public void setUser(UserDto user) {
		this.user = user;
	}
	public String getAddressId() {
		return addressId;
	}
	public void setAddressId(String addressId) {
		this.addressId = addressId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	
}
