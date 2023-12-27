package com.shopping.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.dto.ProductDTO;
import com.shopping.entity.Product;
import com.shopping.service.ProductService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/admin")
public class ProductAdminController {

	@Autowired
	ProductService productService ;
	
	@Autowired
	ModelMapper mapper;
	
	@PostMapping("/save")
	public ResponseEntity<ProductDTO> save(@RequestBody @Valid ProductDTO productDTO){
		log.info("Saving Products controller");
		
		Product savedProduct = productService.saveProduct(mapper.map(productDTO, Product.class));
		
		return new ResponseEntity<>(mapper.map(savedProduct, ProductDTO.class), HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	public ResponseEntity<ProductDTO> update(@RequestBody @Valid ProductDTO dto){
		log.info("Updating Product");
		Product product = mapper.map(dto, Product.class);
		return new ResponseEntity<>(mapper.map(productService.updateProduct(product), ProductDTO.class)  , HttpStatus.OK);
	}
	
	@DeleteMapping("/del/{productId}")
	public ResponseEntity<Boolean> delete(@PathVariable("productId") int productId){
		log.info("Deleting Product");
		return new ResponseEntity<>(productService.removeProduct(productId), HttpStatus.OK);
	}
	
	
	
}
