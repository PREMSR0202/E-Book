package com.service;

import com.model.Role;

public interface RoleService {
	void save(Role role);

	Role findByName(String name);

	public void init();
}