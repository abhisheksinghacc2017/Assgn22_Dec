package com.shopping.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.shopping.dto.ProductDTO;
import com.shopping.entity.Product;
import com.shopping.service.ProductService;
import com.shopping.util.AppUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class MQControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ProductService service;
	
	@MockBean
	RabbitTemplate rabbitTemplate ;

	ProductDTO sampleDTO = new ProductDTO(1, "TestProduct",  "TestDesc" , 900);
	
	Product sampleEntity = new Product(1, "TestProduct", 900, "TestDesc");
	
	String token ;
	
	@BeforeEach
	public void generateJwtToken_Customer() {
		if( null == token || token.trim().length() == 0) {
			
			log.info("Token Not Found. Generating token..");
			
			token = AppUtils.generateJwtToken_Customer();
			
			log.info("Token generated!!");
		}
	}
	
	
	@Test
	public void testSelect() throws Exception {
		Mockito.when(service.findProduct(anyInt())).thenReturn(sampleEntity);
		
		Mockito.doNothing().when(rabbitTemplate).convertAndSend(anyString(), anyString());
		
		
		RequestBuilder reqBuilder =  MockMvcRequestBuilders
			.get("/product/select/1")
			.accept(MediaType.APPLICATION_JSON)
			.header("Authorization", token)
			.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		log.info("Response Content : " + response.getContentAsString());
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	

}
