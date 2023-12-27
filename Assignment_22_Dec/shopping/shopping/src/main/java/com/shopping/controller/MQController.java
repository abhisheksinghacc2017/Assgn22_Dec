package com.shopping.controller;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.entity.Product;
import com.shopping.service.ProductService;

@RestController
@RequestMapping("/product")
public class MQController {

	@Autowired
	ProductService service;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Value("${mq_name}")
	private String mqName;

	@GetMapping("/select/{id}")
	public ResponseEntity<String> select(@PathVariable("id") int productId)
			throws JsonProcessingException, AmqpException {

		Product product = service.findProduct(productId);

		rabbitTemplate.convertAndSend(mqName, new ObjectMapper().writeValueAsString(product));

		return new ResponseEntity<String>("Selected product sent to mq : " + product.toString(), HttpStatus.OK);

	}

}
