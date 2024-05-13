package com.ecommerce.productSecurity.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.productSecurity.entity.Cart;
import com.ecommerce.productSecurity.entity.Product;
import com.ecommerce.productSecurity.entity.User;

@Repository
public interface CartDAO extends CrudRepository<Cart, Integer>{

	public List<Cart> findByUser(User user);
	
	public Cart findByUserAndProduct(User user, Product product);
}
