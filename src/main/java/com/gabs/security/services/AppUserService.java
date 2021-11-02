package com.gabs.security.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gabs.security.daos.AppUserDao;

@Service
public class AppUserService implements UserDetailsService{
	
	private final AppUserDao appUserDao;
	
	public AppUserService(AppUserDao appUserDao) {
		super();
		this.appUserDao = appUserDao;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return appUserDao
				.selectAppUserByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(String.format("Username %s not found", username)));
	}

}
