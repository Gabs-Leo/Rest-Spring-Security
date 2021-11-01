package com.gabs.security;

import java.util.Set;

import com.google.common.collect.Sets;

//É necessário importar os enums como static
import static com.gabs.security.UserPermissions.*;

public enum UserRoles {
	STUDENT(Sets.newHashSet()),
	ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE));
	
	private final Set<UserPermissions> permissions;

	private UserRoles(Set<UserPermissions> permissions) {
		this.permissions = permissions;
	}

	public Set<UserPermissions> getPermissions() {
		return permissions;
	}
}
