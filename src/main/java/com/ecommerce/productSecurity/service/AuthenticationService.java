package com.ecommerce.productSecurity.service;

import com.ecommerce.productSecurity.entity.JwtRequest;
import com.ecommerce.productSecurity.entity.JwtResponse;

public interface AuthenticationService {
	public JwtResponse createJwtToken(JwtRequest request);
}
