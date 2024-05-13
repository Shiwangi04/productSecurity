package com.ecommerce.productSecurity.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetails {
	private Integer amount;
	private String currency;
	private String orderId;
	private String key;
	public TransactionDetails(String orderId, Integer amount, String currency, String key) {
		this.orderId = orderId;
		this.amount = amount;
		this.currency = currency;
		this.key = key;
		
	}
	
}
