package com.gabs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
		.csrf().disable()
		.authorizeRequests()
		//Ant Matchers + Permit All = Marca páginas que não necessitam autenticação
		.antMatchers("/").permitAll()
		.antMatchers("/api/**").hasRole(UserRoles.ADMIN.name())
//		.antMatchers(HttpMethod.DELETE, "/management/api/**").hasAuthority(UserPermissions.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.POST, "/management/api/**").hasAuthority(UserPermissions.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.PUT, "/management/api/**").hasAuthority(UserPermissions.COURSE_WRITE.getPermission())
//		.antMatchers(HttpMethod.GET, "/management/api/**").hasAnyRole(UserRoles.ADMIN.name(), UserRoles.ADMIN_TRAINEE.name())
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
			.authorities(UserRoles.STUDENT.getGrantedAuthorities())
			.build();
		
		UserDetails aylon = User.builder()
			.username("Syllient")
			.password(passwordEncoder.encode("pass"))
			//.roles(UserRoles.STUDENT.name())
			.authorities(UserRoles.ADMIN.getGrantedAuthorities())
			.build();
		
		UserDetails lorrana = User.builder()
			.username("Lorrana")
			.password(passwordEncoder.encode("pass"))
			//.roles(UserRoles.ADMIN_TRAINEE.name())
			.authorities(UserRoles.ADMIN_TRAINEE.getGrantedAuthorities())
			.build();
		
		return new InMemoryUserDetailsManager(
			gabs, aylon, lorrana
		);
	}
	
}
