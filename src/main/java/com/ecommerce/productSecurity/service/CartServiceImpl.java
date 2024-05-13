package com.ecommerce.productSecurity.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ecommerce.productSecurity.CartResponse;
import com.ecommerce.productSecurity.dao.CartDAO;
import com.ecommerce.productSecurity.dao.ProductDAO;
import com.ecommerce.productSecurity.dao.UserDAO;
import com.ecommerce.productSecurity.entity.Cart;
import com.ecommerce.productSecurity.entity.Product;
import com.ecommerce.productSecurity.entity.User;
import com.ecommerce.productSecurity.exception.ErrorResponse;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartDAO dao;
	
	@Autowired
	private ProductDAO prodDao;
	
	@Autowired
	private UserDAO userDao;
	
	public CartResponse addToCart(Integer productId) {
		Product prod = prodDao.findById(productId).get();
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = null;
		if (username != null) {
			user = userDao.findById(username).get();
		}
		if (prod != null && user != null) {
			Cart cartItem = dao.findByUserAndProduct(user, prod);
			if (cartItem == null) {
				// else add
				Cart cart = new Cart(prod, user);
				return new CartResponse(dao.save(cart), null);
			} else {
				//if already present in cart then do nothing instead show an info that object already present and the product
				//quantity can be increased while buying
				return new CartResponse(cartItem, "Item is already present in the cart!!");
			}
			
		}
		return null;
	}
	
	public List<Cart> getCartDetails() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = null;
		if (username != null) {
			user = userDao.findById(username).get();
		}
		if (user != null) {
			return dao.findByUser(user);
		}
		return null;
	}

	@Override
	public void deleteCartItem(Integer cartId) {
		dao.deleteById(cartId);
	}
}
