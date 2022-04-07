package com.pesol.spring.service;

import com.pesol.spring.entity.User;
import com.pesol.spring.model.UserRegisterModel;

public interface UserService {

	User findByUsername(String username);
	
	void save(UserRegisterModel tempUser);
}
