package com.example.firebasedummyserver.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.example.firebasedummyserver.model.entities.EntityUser;
import com.example.firebasedummyserver.service.UserDetailsServiceImpl;
import com.example.firebasedummyserver.model.FirebaseAuthenticationToken;
import com.example.firebasedummyserver.model.MyUserDetails;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

@Component
public class FirebaseIdTokenAuthenticationProvider implements AuthenticationProvider {
	public static final Logger logger = LoggerFactory.getLogger(FirebaseIdTokenAuthenticationProvider.class);

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final FirebaseAuthenticationToken token = (FirebaseAuthenticationToken) authentication;

		try {
			final FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token.getIdToken());

			final String uid = firebaseToken.getUid();
			final UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
			logger.info("User fetched, uid:{}", userRecord.getUid());
			Authentication authentication2 = new EntityUser(userRecord);
			MyUserDetails userFromDatabase = (MyUserDetails) userDetailsService.loadUserByUsername(((EntityUser) authentication2).getUsername());
			((EntityUser) authentication2).setRoles(userFromDatabase.getUser().getRoles());
			return authentication2;
		} catch (FirebaseAuthException e) {
			if (e.getErrorCode().equals("id-token-revoked")) {
				throw new SecurityException("User token has been revoked, please sign in again");
			} else {
				throw new SecurityException("Authentication failed!");
			}
		}
	}

	@Override
	public boolean supports(final Class<?> aClass) {
		return aClass.isAssignableFrom(FirebaseAuthenticationToken.class);
	}

}
