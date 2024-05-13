package com.ecommerce.productSecurity.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productSecurity.entity.OrderDetails;
import com.ecommerce.productSecurity.entity.OrderInput;
import com.ecommerce.productSecurity.entity.TransactionDetails;
import com.ecommerce.productSecurity.service.OrderDetailsService;

@RestController
public class OrderDetailsController {

	@Autowired
	private OrderDetailsService service;
	
	@PostMapping("/placeOrder/{isSingleCheckout}")
	@PreAuthorize("hasRole('user')")
	public void placeOrder(@RequestBody OrderInput orderInput, @PathVariable("isSingleCheckout") boolean isSingleCheckout) {
		service.placeOrder(orderInput, isSingleCheckout);
	}
	
	@GetMapping("/getOrderDetails")
	@PreAuthorize("hasRole('user')")
	public List<OrderDetails> getOrderDetails() {
		return service.getOrderDetails();
	}
	
	@DeleteMapping("/deleteOrder/{orderId}")
	@PreAuthorize("hasRole('user')")
	public void deleteOrder(@PathVariable("orderId") Integer orderId) {
		service.deleteOrder(orderId);
	}
	
	@GetMapping("/getAllOrders/{status}")
	@PreAuthorize("hasRole('admin')")
	public List<OrderDetails> getAllOrders(@PathVariable("status") String status) {
		return service.getAllOrders(status);
	}
	
	@GetMapping("/markOrderStatus/{orderId}")
	@PreAuthorize("hasRole('admin')")
	public void markOrderStatus(@PathVariable("orderId") Integer orderId) {
		service.markOrderStatus(orderId);
	}
	
	@GetMapping("/createTransaction/{amount}")
	@PreAuthorize("hasRole('user')")
	public TransactionDetails createTransaction(@PathVariable("amount") Double amount) {
		return service.createTransaction(amount);
	}
}
