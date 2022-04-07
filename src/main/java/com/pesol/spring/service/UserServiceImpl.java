package com.pesol.spring.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pesol.spring.entity.Role;
import com.pesol.spring.entity.User;
import com.pesol.spring.model.UserRegisterModel;
import com.pesol.spring.repository.RoleRepository;
import com.pesol.spring.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public void save(UserRegisterModel tempUser) {
		
		Role tempRole = roleRepository.findByName("ROLE_USER");
		
		User user = new User(tempUser.getFirstName(), tempUser.getLastName(), 
				tempUser.getEmail(), tempUser.getUsername(), 
				passwordEncoder.encode(tempUser.getPassword()), 
				Set.of(tempRole));
		
		userRepository.save(user);
	}

}
