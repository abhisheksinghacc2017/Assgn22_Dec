package com.consumer.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.consumer.entity.Product;
import com.consumer.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MQService {
	
	@Value("${mq_name}")
	private String mqName ;
	
	@Autowired
	ProductService service;
	
	@RabbitListener(queues = "${mq_name}")
    public void receiveMessage(String message) throws JsonMappingException, JsonProcessingException {
        log.info("Received message: " + message);
        
        if( null != message &&
        		message.trim().length() > 0) {
        
        	Product product = new ObjectMapper().readValue(message, Product.class);
        	
        	log.info("Saving received product to database");
        	
        	Product saved = service.saveProduct(product);
        	
        	log.info("Saved Product : "+ saved.toString());
        }
         
        
    }

}
