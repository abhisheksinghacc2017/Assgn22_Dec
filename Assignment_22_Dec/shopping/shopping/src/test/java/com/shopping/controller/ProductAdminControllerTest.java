package com.shopping.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

import java.util.Date;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.dto.ProductDTO;
import com.shopping.entity.Product;
import com.shopping.service.ProductService;
import com.shopping.util.AppUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@WithMockUser
public class ProductAdminControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	ProductService service;

	ProductDTO sampleDTO = new ProductDTO(1, "TestProduct",  "TestDesc" , 900);
	
	Product sampleEntity = new Product(1, "TestProduct", 900, "TestDesc");
	
	String token ;
	
	@BeforeEach
	public void generateJwtToken_Admin() {
		if( null == token || token.trim().length() == 0) {
			
			log.info("Token Not Found. Generating token..");
			
			token = AppUtils.generateJwtToken_Admin();
			
			log.info("Token generated!!");
		}
	}
	
	
	@Test
	public void testSave() throws Exception {
		Mockito.when(service.saveProduct(any())).thenReturn(sampleEntity);
		
		RequestBuilder reqBuilder =  MockMvcRequestBuilders
			.post("/admin/save")
			.accept(MediaType.APPLICATION_JSON)
			.header("Authorization", token)
			.content(new ObjectMapper().writeValueAsString(sampleDTO))
			.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
	}
	
	@Test
	public void testUpdate() throws Exception {
		Mockito.when(service.updateProduct(any())).thenReturn(sampleEntity);
		
		RequestBuilder reqBuilder =  MockMvcRequestBuilders
			.put("/admin/update")
			.accept(MediaType.APPLICATION_JSON)
			.header("Authorization", token)
			.content(new ObjectMapper().writeValueAsString(sampleDTO))
			.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	public void testDel() throws Exception {
		Mockito.when(service.removeProduct(anyInt())).thenReturn(true);
		
		RequestBuilder reqBuilder =  MockMvcRequestBuilders
			.delete("/admin/del/1")
			.accept(MediaType.APPLICATION_JSON)
			.header("Authorization", token)
			.contentType(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
	}
	
	@Test
	public void testHealth() throws Exception {
		
		RequestBuilder reqBuilder =  MockMvcRequestBuilders
			.get("/app/health")
			.accept(MediaType.APPLICATION_JSON) 
			.header("Authorization", token)
			;
		
		MvcResult result = mockMvc.perform(reqBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		String resContent = response.getContentAsString();
		
		log.info("Response Content : "+ resContent);
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		
		
	}
	
	
}
