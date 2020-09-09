package com.example.firebasedummyserver.model.entities;

import java.util.*;

import javax.persistence.*;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.firebase.auth.UserRecord;
import com.google.firebase.database.annotations.NotNull;
import com.google.firebase.internal.NonNull;

@Entity
@Table(name = "users")
public class EntityUser  implements Authentication {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 8176895017470969924L;

	public EntityUser() {}

	public EntityUser(UserRecord userRecord) {
		super();
		String username = UserUtil.extractUsernameFromEmail(userRecord.getEmail());
		setEmail(userRecord.getEmail());
		setUsername(username);
		setName(userRecord.getDisplayName());
		setImageUrl(userRecord.getPhotoUrl());
		setPhoneNumber(userRecord.getPhoneNumber());		
		setEmailVerified(userRecord.isEmailVerified());
	}

	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
	private long id;

	@NonNull
	private String name;

	@NotNull
	@Column(unique = true)
	private String email;

	@NotNull
	@Column(unique = true)
	private String username;
	
	@Column(name = "imageUrl")
	private String imageUrl;

	@Column(name = "phoneNumber")
	private String phoneNumber;

	@Column(nullable = false)
	private Boolean isEmailVerified = false;

	private Boolean isAuthenticated = false;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_name"))
	private List<EntityRole> roles = new ArrayList<>();

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<EntityRole> roles = getRoles();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();

		for (EntityRole role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		return authorities;
	}

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getDetails() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null ;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Boolean isEmailVerified() {
		return isEmailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.isEmailVerified = emailVerified;
	}

	/**
	 * @return the roles
	 */
	public List<EntityRole> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<EntityRole> roles) {
		this.roles = roles;
	}

	public void setRole(EntityRole role){
		this.roles.add(role);
	}

}
