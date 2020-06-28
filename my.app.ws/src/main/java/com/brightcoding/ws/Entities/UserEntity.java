package com.brightcoding.ws.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity(name="users")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -2219428815803283258L;

	@Id
	@GeneratedValue
	private long id;
	
	@Column(nullable = false)
	private String userId;
	
	@Column(nullable = false, length = 50)
	private String firstName;
	
	@Column(nullable = false, length = 50)
	private String lastName;
	
	@Column(nullable = false, length = 120)
	private String email;
	
	@Column(nullable = false, unique = true)
	private String encreptedPassword;
	
	@Column(nullable = true)
	private String emailVerificationToken;
	//@Column(columnDefinition = "Boolean defult fulse")
	
	@Column(nullable = false)
	private Boolean emailVerificationStatus=false;
	
	@OneToMany(mappedBy ="user",fetch = FetchType.EAGER, cascade = CascadeType.ALL )	//mappedBy : Lier les adresses avec l'objet user de la classe AdressEntity. Chaque objet user peut avoir plusieurs Adress
	private List<AddressEntity> addresses;	//Cascade: créer une liste des adress dans tous les cas (Get/Post/Delete..)

	@OneToOne(mappedBy = "user",fetch = FetchType.EAGER, cascade = CascadeType.ALL)	//Prendre en considération toutes les modifications au contact (delete/update...all)
	private ContactEntity contact;	//OneToOne: Un utilisateur peut avoir un et un seul contact.	Eager effectu le minimun possible des RequestSql recommandée pour les petites données
	
	@ManyToMany(mappedBy ="users", fetch = FetchType.LAZY, cascade = CascadeType.ALL)	//Lazy effectuer plusieurs request, recommandé pour les frandes données
	private Set<GroupEntity> groups= new HashSet<>();	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEncreptedPassword() {
		return encreptedPassword;
	}

	public void setEncreptedPassword(String encreptedPassword) {
		this.encreptedPassword = encreptedPassword;
	}

	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public Boolean getEmailVerificationStatus() {
		return emailVerificationStatus;
	}

	public void setEmailVerificationStatus(Boolean emailVerificationStatus) {
		this.emailVerificationStatus = emailVerificationStatus;
	}

	public List<AddressEntity> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<AddressEntity> addresses) {
		this.addresses = addresses;
	}

	public ContactEntity getContact() {
		return contact;
	}

	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}

	public Set<GroupEntity> getGroups() {
		return groups;
	}

	public void setGroups(Set<GroupEntity> groups) {
		this.groups = groups;
	}
	
	
}
