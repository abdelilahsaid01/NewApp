package com.brightcoding.ws.Entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity(name = "groups")
public class GroupEntity implements Serializable {

	private static final long serialVersionUID = -5299693850863027685L;
	@Id
	@GeneratedValue
	private long id;
	@Column(name = "name", length = 30)
	private String name;

	// Plusieurs groupes peuvent avoir plusieur users
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL) // Recemmandé dans le cas de ManyToMany
	@JoinTable(name = "groups_users", joinColumns = { @JoinColumn(name = "groups_id") }, inverseJoinColumns = {@JoinColumn(name = "users_id") }) // les foreincky des deux tables (users et groups dans groups_users)
	private Set<UserEntity> users = new HashSet<>(); // Joue le role d'une liste mais les attribus ne se répéte pas.
														// JoinTable est le nom de la table intermédiaire

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Set<UserEntity> getUsers() {
		return users;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUsers(Set<UserEntity> users) {
		this.users = users;
	}

}
