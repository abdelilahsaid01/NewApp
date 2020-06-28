package com.brightcoding.ws.Entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

@Entity(name = "contacts")
public class ContactEntity implements Serializable {

	private static final long serialVersionUID = -3444000835364757258L;
	
	@Id @GeneratedValue
	private long id;
	@NotBlank @Column(length = 30)
	private String contactId;
	@NotBlank
	private String mobile;
	private String skype;
	@OneToOne @JoinColumn(name = "users_id")	//mettre le nom de la table et  son id
	private UserEntity user;	//Un contact appartient à un seul utilisateur
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getContactId() {
		return contactId;
	}
	public void setContactId(String contactId) {
		this.contactId = contactId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSkype() {
		return skype;
	}
	public void setSkype(String skype) {
		this.skype = skype;
	}
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	

}
