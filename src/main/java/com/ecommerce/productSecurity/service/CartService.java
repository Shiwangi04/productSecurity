package com.ecommerce.productSecurity.service;

import java.util.List;

import com.ecommerce.productSecurity.CartResponse;
import com.ecommerce.productSecurity.entity.Cart;

public interface CartService {

	public CartResponse addToCart(Integer productId);
	
	public List<Cart> getCartDetails();

	public void deleteCartItem(Integer cartId);
}
