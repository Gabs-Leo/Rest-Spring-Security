package com.gabs.security.daos;

import java.util.Optional;

import com.gabs.security.entities.AppUser;

public interface AppUserDao {
	public Optional<AppUser> selectAppUserByUsername(String username);
}
