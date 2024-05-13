package com.ecommerce.productSecurity.dao;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.productSecurity.entity.OrderDetails;
import com.ecommerce.productSecurity.entity.User;

@Repository
public interface OrderDetailsDAO extends CrudRepository<OrderDetails, Integer>{
	public List<OrderDetails> findByUser(User user);
	public List<OrderDetails> findByOrderStatus(String status);
}
