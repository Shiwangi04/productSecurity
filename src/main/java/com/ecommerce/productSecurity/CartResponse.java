package com.ecommerce.productSecurity;

import com.ecommerce.productSecurity.entity.Cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartResponse {
	private Cart cart;
	private String message;
	public CartResponse(Cart cart, String message) {
		this.cart = cart;
		this.message = message;
	}
	
	
}
