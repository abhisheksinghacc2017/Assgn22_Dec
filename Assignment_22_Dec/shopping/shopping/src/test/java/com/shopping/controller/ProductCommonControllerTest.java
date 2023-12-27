package com.shopping.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(value = ProductCommonController.class)
@WithMockUser
public class ProductCommonControllerTest {
	

	@Autowired
	private MockMvc mockMvc;

	
	@MockBean
	ProductService service;

	ProductDTO sampleDTO = new ProductDTO(1, "TestProduct",  "TestDesc" , 900);
	
	Product sampleEntity = new Product(1, "TestProduct", 900, "TestDesc");
	
	String token ;
	
	@BeforeEach
	public void generateJwtToken() {
		if( null == token || token.trim().length() == 0) {
			
			System.out.println("Token Not Found. Generating token..");
			
			token = AppUtils.generateJwtToken_Admin();
			
			System.out.println("Token generated!!");
		}
	}

	@Test
	public void testAll() throws Exception {

		Mockito.when(service.findAll()).thenReturn(Arrays.asList(sampleEntity));

		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/shop/products")
				.accept(MediaType.APPLICATION_JSON)
				.header("Authorization", token);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();
		
		log.info("Response Content : "+ response.getContentAsString());

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}	
	
	

}
