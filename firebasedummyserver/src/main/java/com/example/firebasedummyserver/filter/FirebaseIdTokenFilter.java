package com.example.firebasedummyserver.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.firebasedummyserver.model.FirebaseAuthenticationToken;
import com.example.firebasedummyserver.provider.FirebaseIdTokenAuthenticationProvider;

public class FirebaseIdTokenFilter extends OncePerRequestFilter {
	public static final Logger logger = LoggerFactory.getLogger(FirebaseIdTokenFilter.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");
		String idToken = authorization.replace("Bearer ", "");
		SecurityContextHolder.getContext().setAuthentication(new FirebaseAuthenticationToken(idToken));
		chain.doFilter(request, response);
	}

}
