package com.example.firebasedummyserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	private final FirebaseIdTokenAuthenticationProvider firebaseAuthenticationProvider;

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Autowired
	public SecurityConfig(FirebaseIdTokenAuthenticationProvider authenticationProvider) {
		this.firebaseAuthenticationProvider = authenticationProvider;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new FirebaseIdTokenFilter(), BasicAuthenticationFilter.class).authorizeRequests()
				.antMatchers("/googleuser").permitAll().antMatchers("/hello").hasAuthority("ADMIN").anyRequest().authenticated();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(firebaseAuthenticationProvider).authenticationProvider(daoAuthenticationProvider());
	}
}
