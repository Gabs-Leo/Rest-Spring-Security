package com.gabs.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
		//.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
		//.and()
		.csrf().disable()
		.authorizeRequests()
		//Ant Matchers + Permit All = Marca páginas que não necessitam autenticação
		.antMatchers("/").permitAll()
		.antMatchers("/api/**").hasAnyRole(UserRoles.STUDENT.name(), UserRoles.ADMIN.name())
		.anyRequest().authenticated()
		.and()
		.formLogin()
		//Utilizando página de login personalizada
		.loginPage("/login").permitAll()
		.defaultSuccessUrl("/courses", true)
		.and()
		.rememberMe() //2 semanas por padrão
			.tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21)) // Adicionando tempo porsonalizado
			.key("somethingsecured")
		.and()
		.logout()
			.logoutUrl("/logout") //Personalizar rota de logout
			.clearAuthentication(true)
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID", "rememberMe", "_gid", "_ga")
			.logoutSuccessUrl("/login"); //Personalizar logout success
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
			.username("Aylon")
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
