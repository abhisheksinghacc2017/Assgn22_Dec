package com.consumer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {

	@GetMapping(path = "/health")
	public ResponseEntity<String> health(){
		return new ResponseEntity("Congratulations!! Your Application is up", HttpStatus.OK);
	}
	
}
