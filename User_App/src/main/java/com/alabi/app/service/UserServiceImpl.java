package com.alabi.app.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.alabi.app.config.Encoder;
import com.alabi.app.entity.Role;
import com.alabi.app.entity.User;
import com.alabi.app.repository.UserRepository;
import com.alabi.app.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	private Encoder passwordEncoder;

	public UserServiceImpl() {
		super();
	}

	@Autowired
	public UserServiceImpl(UserRepository userRepository, Encoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
				User user = new User(registrationDto.getFirstName(), registrationDto.getLastName(),
				registrationDto.getEmail(), passwordEncoder.encode(registrationDto.getPassword()),
				registrationDto.getRoles());
				
	return userRepository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		
		if(user == null) {
			System.out.println("email :::::" + username);				
			throw new UsernameNotFoundException("Invalid username or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}
	
	@Override
	public List<User> read() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findById(id).get();
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	
	public void create(User user) {
		User user1 = new User(user.getId(), user.getFirstName(), user.getLastName(),
				user.getEmail(), passwordEncoder.encode(user.getPassword()),
				user.getRoles());
		userRepository.save(user1);
}
	
	@Override
	public void edit(User user) {
		Long id = user.getId();
		String pass = (userRepository.findById(id).get()).getPassword();
		
		user.setPassword(pass);
		userRepository.save(user);
	}
	
	
}
