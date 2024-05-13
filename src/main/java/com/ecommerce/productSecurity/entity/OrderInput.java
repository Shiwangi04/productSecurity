package com.ecommerce.productSecurity.entity;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderInput {

	private String fullName;
	private String fullAddress;
	private String contactNo;
	private String altContactNo;
	private List<OrderProductQuantity> productList;
	private String transactionId;
}
