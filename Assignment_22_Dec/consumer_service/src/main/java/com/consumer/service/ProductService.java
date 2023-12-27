package com.consumer.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.consumer.entity.Product;
import com.consumer.exception.ErrorConstants;
import com.consumer.exception.ResourceNotFoundException;
import com.consumer.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductService {

	@Autowired
	ProductRepository productRepo;


	@Transactional
	public Product saveProduct(Product product) {
		log.info("Saving Product in service");

		Product savedProducts = productRepo.save(product);
		log.info("Product is saved");
		return savedProducts;
	}

	public List<Product> findAll() {
		log.info("Finding Products");
		List<Product> products = productRepo.findAll();
		checkListAndThrowError(products, ErrorConstants.NO_CONTENT);
		return products;
	}
	
	private void checkListAndThrowError(List<Product> products, String errorMsg) {
		if (products != null && products.isEmpty()) {
			throw new ResourceNotFoundException(errorMsg);
		}
	}

}
