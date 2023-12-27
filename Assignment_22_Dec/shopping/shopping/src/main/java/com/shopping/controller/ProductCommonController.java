package com.shopping.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.ProductDTO;
import com.shopping.entity.Product;
import com.shopping.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/shop")
public class ProductCommonController {
	
	@Autowired
	ProductService productService ;
	
	@Autowired
	ModelMapper mapper;
	
	@GetMapping("/products")
	public ResponseEntity<List<ProductDTO>> all(){
		log.info("Finding All products");
		
		List<Product> entities = productService.findAll();
		
		return new ResponseEntity<>(castEntityListToDTO(entities) , HttpStatus.OK);
	}
	
	private List<ProductDTO> castEntityListToDTO(List<Product> list) {
		List<ProductDTO> dtos = list.stream().map( p -> {
			return mapper.map(p, ProductDTO.class);
		}).collect(Collectors.toList());
		return dtos;
	}
	
}
