package com.ecommerce.productSecurity.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ecommerce.productSecurity.entity.ImageModel;
import com.ecommerce.productSecurity.entity.Product;
import com.ecommerce.productSecurity.service.ProductService;

@RestController
public class ProductController {

	@Autowired
	private ProductService service;
	
	@PostMapping(value = {"/product/add"}, consumes= {MediaType.MULTIPART_FORM_DATA_VALUE})
	@PreAuthorize("hasRole('admin')")
	public Product addProduct(@RequestPart("product") Product product, 
			@RequestPart("imageFile") MultipartFile[] file) {
		try {
			Set<ImageModel> imageSet = uploadImage(file);
			product.setProductImages(imageSet);
			return service.addProduct(product);
		} catch (IOException e) {
			System.out.println("Exception occured: "+ e.getMessage());
			return null;
		}

	}
	
	public Set<ImageModel> uploadImage(MultipartFile[] files) throws IOException {
		Set<ImageModel> imageSet = new HashSet<>();
		
		for (MultipartFile file : files) {
			ImageModel model = new ImageModel(
					file.getOriginalFilename(),
					file.getContentType(),
					file.getBytes()
			);
			imageSet.add(model);
		}
		return imageSet;
	}
	
	@GetMapping(value = {"/product/findAll"})
	@PreAuthorize("hasAnyRole('admin')")
	public List<Product> findProducts(@RequestParam(defaultValue="0")int pageNumber,
			@RequestParam(defaultValue="") String searchKey) {
		return service.findProducts(pageNumber, searchKey);
	}
	
	@DeleteMapping(value = {"/product/delete/{productId}"})
	@PreAuthorize("hasRole('admin')")
	public ResponseEntity<Integer> deleteProducts(@PathVariable("productId") Integer productId) {
		return service.deleteProducts(productId);
	}
	
	@GetMapping(value = {"/product/{productId}"})
	public Product getProductById(@PathVariable("productId") Integer productId) {
		return service.getProductById(productId);
	}
	
	@GetMapping(value = {"/product/getAll"})
	public List<Product> getAllProducts(@RequestParam(defaultValue="0")int pageNumber, 
			@RequestParam(defaultValue="") String searchKey) {
		return service.getAllProducts(pageNumber, searchKey);
	}
	@GetMapping(value = {"/getProduct/{isSingleCheckout}/{productId}"})
	@PreAuthorize("hasRole('user')")
	public List<Product> getProductDetails(@PathVariable("productId") Integer productId, @PathVariable("isSingleCheckout") boolean isSingleCheckout) {
		return service.getProductDetails(productId, isSingleCheckout);
	}
}
