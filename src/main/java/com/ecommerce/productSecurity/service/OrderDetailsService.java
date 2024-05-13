package com.ecommerce.productSecurity.service;

import java.util.List;

import com.ecommerce.productSecurity.entity.OrderDetails;
import com.ecommerce.productSecurity.entity.OrderInput;
import com.ecommerce.productSecurity.entity.TransactionDetails;

public interface OrderDetailsService {

	public void placeOrder(OrderInput orderInput, boolean isCartCheckout);

	public List<OrderDetails> getOrderDetails();

	public void deleteOrder(Integer orderId);

	public List<OrderDetails> getAllOrders(String status);

	public void markOrderStatus(Integer orderId);
	
	public TransactionDetails createTransaction(Double amount);
}
