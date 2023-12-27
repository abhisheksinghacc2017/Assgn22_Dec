package com.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authservice.entity.ApplicationUser;
import com.authservice.repository.ApplicationUserRepository;

@Service
public class AuthService {

	@Autowired
	ApplicationUserRepository repo;

	@Autowired
	PasswordEncoder encoder;

	public ApplicationUser save(ApplicationUser user) {
		user.setPassword(encoder.encode(user.getPassword()));
		return repo.save(user);
	}
	
	public List<ApplicationUser> getAll(){
		return repo.findAll();
	}

}
