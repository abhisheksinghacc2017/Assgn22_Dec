package com.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authservice.entity.ApplicationUser;
import com.authservice.service.AuthService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	AuthService service;
	
	@GetMapping(value = "/health")
	public ResponseEntity<String> up() {
		System.out.println("hello------------");
		return  new ResponseEntity( "Your application is running" , HttpStatus.OK);
	}
	
	@PostMapping(value = "/save")
	public ResponseEntity<ApplicationUser> createUser(@RequestBody ApplicationUser user) {
		System.out.println("Saving user");
		return new  ResponseEntity( service.save(user) , HttpStatus.CREATED );
	}
	
	@GetMapping(value = "/users")
	public ResponseEntity<List<ApplicationUser>> all(){
		System.out.println("Fetching all user");
		return new  ResponseEntity(service.getAll(), HttpStatus.OK);
	}
	
}
