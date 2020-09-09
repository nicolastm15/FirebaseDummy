package com.example.firebasedummyserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.example.firebasedummyserver.filter.FirebaseIdTokenFilter;
import com.example.firebasedummyserver.provider.FirebaseIdTokenAuthenticationProvider;
import com.example.firebasedummyserver.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@Component
@EnableGlobalMethodSecurity(securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	FirebaseIdTokenAuthenticationProvider firebaseAuthenticationProvider;

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new FirebaseIdTokenFilter(), BasicAuthenticationFilter.class).authorizeRequests()
				.antMatchers("/basic/*").hasAnyRole("ADMIN","BASIC").antMatchers("/admin/*").hasRole("ADMIN")
				.antMatchers("/basic/user/google").permitAll();
	}

}
