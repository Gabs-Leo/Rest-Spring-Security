package com.gabs.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		//Basic Auth
		http
		.authorizeRequests()
		//Ant Matchers + Permit All = Marca páginas que não necessitam autenticação
		.antMatchers("/").permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic();
	}
	
}
