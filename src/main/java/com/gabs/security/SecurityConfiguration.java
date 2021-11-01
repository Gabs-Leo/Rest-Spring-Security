package com.gabs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public SecurityConfiguration(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}
	
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

	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails gabs = User.builder()
			.username("Gabs")
			.password(passwordEncoder.encode("pass"))
			.roles("STUDENT") //ROLE_STUDENT
			.build();
		
		UserDetails aylon = User.builder()
			.username("Syllient")
			.password(passwordEncoder.encode("fodase"))
			.roles("ADMIN")
			.build();
		
		return new InMemoryUserDetailsManager(
			gabs, aylon
		);
	}
	
}
