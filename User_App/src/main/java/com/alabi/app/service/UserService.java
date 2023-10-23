package com.alabi.app.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.alabi.app.entity.User;
import com.alabi.app.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService{

	User save(UserRegistrationDto registrationDto);
	void create(User user);
	List<User> read();
	User findById(Long id);
	void deleteById(Long id);
	 void edit(User user);
}
