package com.ecommerce.productSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productSecurity.entity.Role;
import com.ecommerce.productSecurity.service.RoleService;

@RestController
public class RoleController {

	@Autowired
	private RoleService service;
	
	@PostMapping("/createRole")
	public Role createRole(@RequestBody Role role) {
		return service.createRole(role);	
	}
}
