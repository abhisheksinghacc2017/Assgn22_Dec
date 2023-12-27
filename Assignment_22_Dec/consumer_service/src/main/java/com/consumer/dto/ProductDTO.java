package com.consumer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

	private int productId;
	
	@NotBlank(message = "Name can not be empty")
	private String name;

	@NotBlank(message = "Description can not be empty")
	private String description;

	@Positive (message = "Price should be positive")
	private double price;
	
}
