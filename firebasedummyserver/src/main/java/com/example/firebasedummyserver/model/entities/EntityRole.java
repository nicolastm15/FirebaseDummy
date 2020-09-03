package com.example.firebasedummyserver.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * This class describes the roles a user can have, like admin, simple user, and
 * any other role that may be created in the future. Each role defines a set of
 * permissions for a particular user.
 * 
 * @see EntityUser
 */

@Entity
@Table(name = "roles")
public class EntityRole {

	@Id
	@Column(name = "role_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer id;
	
	@Column(name = "role_name", unique = true)
	@NotNull
	private String name;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
