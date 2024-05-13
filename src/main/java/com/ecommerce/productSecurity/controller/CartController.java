package com.ecommerce.productSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productSecurity.CartResponse;
import com.ecommerce.productSecurity.entity.Cart;
import com.ecommerce.productSecurity.exception.ErrorResponse;
import com.ecommerce.productSecurity.service.CartService;

@RestController
public class CartController {

	@Autowired
	private CartService service;
	
	@PreAuthorize("hasRole('user')")
	@GetMapping("/addToCart/{productId}")
	public CartResponse addToCart(@PathVariable("productId") Integer productId) {
		return service.addToCart(productId);
	}
	
	@PreAuthorize("hasRole('user')")
	@GetMapping("/getCartDetails")
	public List<Cart> getCartDetails() {
		return service.getCartDetails();
	}
	
	@PreAuthorize("hasRole('user')")
	@DeleteMapping("/deleteCartItem/{cartId}")
	public void deleteCartItem(@PathVariable("cartId") Integer cartId) {
		service.deleteCartItem(cartId);
	}
}
