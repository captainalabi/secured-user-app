package com.alabi.app.service;

import java.util.List;

import com.alabi.app.entity.Role;



public interface RoleService {

	void createRole(Role role);
	List<Role> readRole();
	Role findById(Long id);
	void deleteById(Long id);
}
