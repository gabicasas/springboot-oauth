package com.gcc.app.oauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class SpringSecurityConf extends WebSecurityConfigurerAdapter
{

	
	@Autowired
	private UserDetailsService userService;
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); 
	}
	
	@Autowired
	AuthenticationEventPublisher authenticationEventPublisher;

	
	
	
	 //Se configura como se autentica y como se encripta la pass
	 
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	
		auth.userDetailsService(this.userService).passwordEncoder(passwordEncoder())
		.and().authenticationEventPublisher(authenticationEventPublisher);
	}

	@Override
	@Bean // <-- Solo se ha metido el autentication manager en contexto
	protected AuthenticationManager authenticationManager() throws Exception {
		// TODO Auto-generated method stub
		return super.authenticationManager();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().anyRequest()/*.authenticated()
		.and()
		.formLogin().and().httpBasic()
		.and()
		.logout()*/
		.permitAll();
	}
	
	
}
