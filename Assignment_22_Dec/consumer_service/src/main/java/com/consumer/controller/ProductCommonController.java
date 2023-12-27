package com.consumer.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.consumer.dto.ProductDTO;
import com.consumer.entity.Product;
import com.consumer.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/selected")
public class ProductCommonController {
	
	@Autowired
	ProductService productService ;
	
	@Autowired
	ModelMapper mapper;
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> all(){
		log.info("Finding All products");
		
		List<Product> entities = productService.findAll();
		
		return new ResponseEntity<>(castDTOListToEntity(entities) , HttpStatus.OK);
	}
	
	private List<ProductDTO> castDTOListToEntity(List<Product> list) {
		List<ProductDTO> dtos = list.stream().map( p -> {
			return mapper.map(p, ProductDTO.class);
		}).collect(Collectors.toList());
		return dtos;
	}
	
}
