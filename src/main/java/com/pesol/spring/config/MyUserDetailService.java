package com.pesol.spring.config;

import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.pesol.spring.entity.User;
import com.pesol.spring.repository.UserRepository;

//@Service
public class MyUserDetailService implements UserDetailsService{
	
	
	private static final Logger LOG = LoggerFactory.getLogger(MyUserDetailService.class);


	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = userRepository.findByUsername(username);
		
		if (user == null) {
			throw new UsernameNotFoundException("Username: " + username + " not found");
		}
		
		LOG.info(user.getUsername());
		
		return new MyUserDetail(user.getId(), user.getUsername(), user.getPassword(), 
				user.getRoles().stream()
						.map(role -> new SimpleGrantedAuthority(role.getName()))
						.collect(Collectors.toList()));
	}

	
}
