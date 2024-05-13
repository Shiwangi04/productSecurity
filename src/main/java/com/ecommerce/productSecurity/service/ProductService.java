package com.ecommerce.productSecurity.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.ecommerce.productSecurity.entity.Product;

public interface ProductService {

	public Product addProduct(Product product);
	
	public List<Product> findProducts(int pageNumber, String searchKey);
	
	public List<Product> getAllProducts(int pageNumber, String searchKey);
	
	public ResponseEntity<Integer> deleteProducts(Integer productId);
	
	public Product getProductById(Integer productId);
	
	public List<Product> getProductDetails(Integer productId, boolean isSingleCheckout);
}
