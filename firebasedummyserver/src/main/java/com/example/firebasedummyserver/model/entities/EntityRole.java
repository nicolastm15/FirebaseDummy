package com.example.firebasedummyserver.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;

/**
 * This class describes the roles a user can have, like admin, simple user, and
 * any other role that may be created in the future. Each role defines a set of
 * permissions for a particular user.
 * 
 * @see EntityUser
 */

@Entity
@Table(name = "roles")
public class EntityRole implements GrantedAuthority{

	private static final long serialVersionUID = -2823311224048773918L;

	@Id
	@Column(name = "role_name", unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getAuthority() {
		return this.name;
	}

}
