package com.ecommerce.productSecurity.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer orderId;
	private String orderFullName;
	private String orderFullAddress;
	private String orderContactNo;
	private String orderAltContactNo;
	private String orderStatus;
	private Double orderAmount;
	@ManyToOne
	private Product product;
	@ManyToOne
	private User user;
	private String transactionId;
	public OrderDetails(String orderFullName, String orderFullAddress, String orderContactNo, String orderAltContactNo,
			String orderStatus, Double orderAmount, Product product, User user, String transactionId) {
		this.orderFullName = orderFullName;
		this.orderFullAddress = orderFullAddress;
		this.orderContactNo = orderContactNo;
		this.orderAltContactNo = orderAltContactNo;
		this.orderStatus = orderStatus;
		this.orderAmount = orderAmount;
		this.product = product;
		this.user = user;
		this.transactionId = transactionId;
	}
	
	
}
