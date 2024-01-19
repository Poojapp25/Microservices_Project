package com.project.productservice.Service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.productservice.Model.Product;
import com.project.productservice.Repository.ProductRepository;
import com.project.productservice.dto.ProductRequest;
import com.project.productservice.dto.ProductResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
	
//	public ProductService(ProductRepository productRepository) {
//		this.productRepository = productRepository;
//	}
	private final ProductRepository productRepository;
	
	public void createProduct(ProductRequest productRequest) {
		Product product = Product.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.build();
		productRepository.save(product);
//		log.info("Product " + product.getId() + " Saved.");
		log.info("Product {} is Saved.", product.getId());
	}

	public List<ProductResponse> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products.stream().map(product -> mapToProductResponse(product)).toList();
	}
	private ProductResponse mapToProductResponse(Product product) {
		return ProductResponse.builder()
				.id(product.getId())
				.name(product.getName())
				.description(product.getDescription())
				.price(product.getPrice())
				.build();
	}
}
