package com.consumer.config;

import org.modelmapper.ModelMapper;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	
	//@Value("${mq_name}")
	//private String mqName;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	/*
	@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        
        return rabbitTemplate;
    }
    
    @Bean
	public Queue myQueue() {
		return new Queue(mqName, true, false, false);
	}	*/

}
