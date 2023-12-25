package com.alabi.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.alabi.app.entity.User;
import com.alabi.app.entity.UserDTO;

public interface UserService extends UserDetailsService{

	User save(UserDTO userDTO);
	void create(UserDTO userDTO);
	List<UserDTO> read();
	UserDTO findById(Long id);
	void deleteById(Long id);
	void edit(UserDTO userDTO);
	UserDTO findByEmail(String email);
}
