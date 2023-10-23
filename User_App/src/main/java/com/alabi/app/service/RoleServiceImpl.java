package com.alabi.app.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alabi.app.entity.Role;
import com.alabi.app.repository.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService{

	private RoleRepository repository;
	
	@Autowired
	public RoleServiceImpl(RoleRepository repository) {
		super();
		this.repository = repository;
	}

	@Override
	public void createRole(Role role) {
		repository.save(role);
	}

	@Override
	public List<Role> readRole() {
		return repository.findAll();
	}

	@Override
	public Role findById(Long id) {
		return repository.findById(id).get();
	}

	@Override
	public void deleteById(Long id) {
		repository.deleteById(id);
	}

}
