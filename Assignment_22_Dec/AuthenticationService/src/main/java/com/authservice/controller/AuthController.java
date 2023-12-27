package com.authservice.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.authservice.entity.ApplicationUser;
import com.authservice.repository.ApplicationUserRepository;
import com.authservice.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthenticationManager manager;
	
	
	
	@GetMapping("/home")
	public String msg() {
		return "You are authorized user";
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> authenticate(@RequestBody ApplicationUser request) {
		
		try {		
		Authentication a=manager.
		authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		
		UserDetails userDetails =(UserDetails)a.getPrincipal();
		
		String s=JWT.create()
		   .withSubject(userDetails.getUsername())
		   .withIssuedAt(new Date(System.currentTimeMillis()))
		   .withExpiresAt(new Date(System.currentTimeMillis()+(1000*60*60*5)))
		   .withClaim("roles", userDetails.getAuthorities()
				   .stream()
				   .map(t-> t.getAuthority())
				   .collect(Collectors.toList())
		    ).sign(Algorithm.HMAC256("helloworld"));
		
		HttpHeaders respHeader=new HttpHeaders();
		respHeader.add("Authorization", "Bearer "+s);
		
		return new ResponseEntity<String>("User is verified",respHeader,HttpStatus.OK);
		
		}catch(Exception e) {
			return new ResponseEntity<String>("User is not verified",HttpStatus.BAD_REQUEST);
			
		}
	}
	
	
}
