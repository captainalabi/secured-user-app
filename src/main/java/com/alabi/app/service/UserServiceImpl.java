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
import com.alabi.app.entity.UserDTO;
import com.alabi.app.entity.UserDTOMapper;
import com.alabi.app.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	@Autowired
	private final UserRepository userRepository;
	@Autowired
	private final Encoder passwordEncoder;
	@Autowired
	private final UserDTOMapper userDTOMapper;

	@Override
	public User save(UserDTO userDTO) {
		User user = new User(
				userDTO.firstName(),
				userDTO.lastName(), 
				userDTO.email(),
				passwordEncoder.encode(userDTO.password()), 
				userDTO.roles());
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
				return new org.springframework.security.core.userdetails.User(
				user.getEmail(),
				user.getPassword(),
				mapRolesToAuthorities(user.getRoles())
				);
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles
				.stream()
				.map(role -> new SimpleGrantedAuthority(role.getName()))
				.collect(Collectors
				.toList());
	}

	@Override
	public List<UserDTO> read() {
		return userRepository.findAll().stream().map(userDTOMapper).collect(Collectors.toList());
	}

	@Override
	public UserDTO findById(Long id) {
		return userRepository.findById(id).map(userDTOMapper).get();
	}

	@Override
	public void deleteById(Long id) {
		userRepository.deleteById(id);
	}

	public void create(UserDTO userDTO) {
		User user = new User(
				userDTO.id(), 
				userDTO.firstName(),
				userDTO.lastName(), 
				userDTO.email(),
				passwordEncoder.encode(userDTO.password()), 
				userDTO.roles());
		userRepository.save(user);
	}

	@Override
	public void edit(UserDTO userDTO) {
		User theUser = userRepository.findById(userDTO.id()).get();
		userRepository.save(
				new User(userDTO.id(), 
						userDTO.firstName(),
						userDTO.lastName(), 
						userDTO.email(),
						theUser.getPassword(),
						userDTO.roles()));
	}

	@Override
	public UserDTO findByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return new UserDTO(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail(),
				user.getPassword(),
				user.getRoles()
				);
	}
}
