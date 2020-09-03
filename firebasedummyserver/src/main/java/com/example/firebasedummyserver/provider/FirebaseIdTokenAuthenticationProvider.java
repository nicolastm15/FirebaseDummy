package com.example.firebasedummyserver.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.example.firebasedummyserver.model.entities.EntityUser;
import com.example.firebasedummyserver.model.FirebaseAuthenticationToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;

@Component
public class FirebaseIdTokenAuthenticationProvider implements AuthenticationProvider {
	public static final Logger logger = LoggerFactory.getLogger(FirebaseIdTokenAuthenticationProvider.class);

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		FirebaseAuthenticationToken token = (FirebaseAuthenticationToken) authentication;

		try {
			FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(token.getIdToken());

			String uid = firebaseToken.getUid();
			UserRecord userRecord = FirebaseAuth.getInstance().getUser(uid);
			logger.info("User fetched, uid:{}", userRecord.getUid());

			return new EntityUser(userRecord);
		} catch (FirebaseAuthException e) {
			if (e.getErrorCode().equals("id-token-revoked")) {
				throw new SecurityException("User token has been revoked, please sign in again");
			} else {
				throw new SecurityException("Authentication failed!");
			}
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return aClass.isAssignableFrom(FirebaseAuthenticationToken.class);
	}

}
