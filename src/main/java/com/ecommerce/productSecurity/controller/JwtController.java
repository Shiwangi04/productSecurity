package com.ecommerce.productSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productSecurity.entity.JwtRequest;
import com.ecommerce.productSecurity.entity.JwtResponse;
import com.ecommerce.productSecurity.service.AuthenticationService;
import com.ecommerce.productSecurity.service.JwtService;

@RestController
@CrossOrigin
public class JwtController {
	
	@Autowired
	private AuthenticationService service;
	
	@PostMapping("/authenticate")
	public JwtResponse createJwtToken(@RequestBody JwtRequest request) throws Exception {
		return service.createJwtToken(request);
	}
}
