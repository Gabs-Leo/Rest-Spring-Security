package com.gabs.security;

//É necessário importar os enums como static
import static com.gabs.security.UserPermissions.COURSE_READ;
import static com.gabs.security.UserPermissions.COURSE_WRITE;
import static com.gabs.security.UserPermissions.STUDENT_READ;
import static com.gabs.security.UserPermissions.STUDENT_WRITE;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum UserRoles {
	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),
	ADMIN_TRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));
	
	private final Set<UserPermissions> permissions;

	private UserRoles(Set<UserPermissions> permissions) {
		this.permissions = permissions;
	}

	public Set<UserPermissions> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getGrantedAuthorities(){
		Set<SimpleGrantedAuthority> permissions = getPermissions().stream()
		.map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
		.collect(Collectors.toSet());
		
		permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		
		return permissions;
	}
}
