package com.ecommerce.productSecurity.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.ecommerce.productSecurity.dao.CartDAO;
import com.ecommerce.productSecurity.dao.ProductDAO;
import com.ecommerce.productSecurity.dao.UserDAO;
import com.ecommerce.productSecurity.entity.Cart;
import com.ecommerce.productSecurity.entity.Product;
import com.ecommerce.productSecurity.entity.User;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private ProductDAO dao;
	
	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private CartDAO cartDao;
	
	@Override
	public Product addProduct(Product product) {
		return dao.save(product);
	}

	@Override
	public List<Product> findProducts(int pageNumber, String searchKey) {
		Pageable pageable = PageRequest.of(pageNumber, 10);
		if (searchKey.equals("")) {
			return (List<Product>)dao.findAll(pageable);
		} else {
			return (List<Product>)dao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
		}
	}

	@Override
	public ResponseEntity<Integer> deleteProducts(Integer productId) throws ResponseStatusException{
		dao.deleteById(productId);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Override
	public Product getProductById(Integer productId) {
		return dao.findById(productId).get();
	}

	@Override
	public List<Product> getProductDetails(Integer productId, boolean isSingleCheckout) {
		List<Product> list = new ArrayList<>();
		if (isSingleCheckout && productId != 0) {
			// we buy single product
			Product prod = dao.findById(productId).get();
			list.add(prod);
			return list;
		} else {
			//we buy entire cart
			String username = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = null;
			if (username != null) {
				user = userDao.findById(username).get();
			}
			if (user != null) {
				List<Cart> carts = cartDao.findByUser(user);
				return carts.stream().map(x -> x.getProduct()).collect(Collectors.toList());
			}
			return null;
		}
	}

	@Override
	public List<Product> getAllProducts(int pageNumber, String searchKey) {
		Pageable pageable = PageRequest.of(pageNumber, 10);
		
		if (searchKey.equals("")) {
			return (List<Product>)dao.findAll(pageable);
		} else {
			return (List<Product>)dao.findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(searchKey, searchKey, pageable);
		}
		
	}

}
