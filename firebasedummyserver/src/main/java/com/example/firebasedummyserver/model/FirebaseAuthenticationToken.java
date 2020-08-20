package com.example.firebasedummyserver.model;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class FirebaseAuthenticationToken extends AbstractAuthenticationToken {

	private final String idToken;
	
	public String getIdToken() {
		return idToken;
	}


	public FirebaseAuthenticationToken(String idToken) {
		super(null);
		this.idToken = idToken;
	}
	

	@Override
	public Object getCredentials() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getPrincipal() {
		// TODO Auto-generated method stub
		return null;
	}

}
