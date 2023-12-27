package com.shopping.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.entity.Product;
import com.shopping.exception.ErrorConstants;
import com.shopping.exception.ResourceNotFoundException;
import com.shopping.repository.ProductRepository;

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

	@Transactional
	public boolean removeProduct(int productId) {
		log.info("Removing Product");
		productRepo.findById(productId).ifPresentOrElse(productRepo::delete, () -> {
			throw new ResourceNotFoundException("Product not found to delete with productId : " + productId);
		});
		log.info("Product remove done");
		return true;
	}

	@Transactional
	public Product updateProduct(Product product) {
		log.info("Updating Product");
		productRepo.findById(product.getProductId()).ifPresentOrElse(p -> {
			productRepo.save(product);
		}, () -> {
			throw new ResourceNotFoundException(
					"Product not found to update with productId : " + product.getProductId());
		});
		log.info("Product update done");
		return productRepo.findById(product.getProductId()).get();
	}

	public List<Product> findAll() {
		log.info("Finding Products");
		List<Product> products = productRepo.findAll();
		checkListAndThrowError(products, ErrorConstants.NO_CONTENT);
		return products;
	}
	
	public Product findProduct(int productId) {
		log.info("Finding Product");
		Optional<Product> product  = productRepo.findById(productId) ;
		
		product.orElseThrow(() -> {
			throw new ResourceNotFoundException(
					"Product not found to select and push it to mq : " + productId);
		});
		
		return product.get();
	}
	
	private void checkListAndThrowError(List<Product> products, String errorMsg) {
		if (products != null && products.isEmpty()) {
			throw new ResourceNotFoundException(errorMsg);
		}
	}

}
