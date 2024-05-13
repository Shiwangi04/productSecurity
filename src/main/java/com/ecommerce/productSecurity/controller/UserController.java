package com.ecommerce.productSecurity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.productSecurity.entity.User;
import com.ecommerce.productSecurity.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService service;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@PostMapping("/registerUser")
	public User registerUser(@RequestBody User user) {
		User inputUser = user.builder()
				.password(encoder.encode(user.getPassword()))
				.email(user.getEmail())
				.userName(user.getUserName())
				.firstName(user.getFirstName())
				.lastName(user.getLastName())
				.roles(user.getRoles())
				.build();
		return service.registerUser(inputUser);
	}
	
	@GetMapping("/admin")
	@PreAuthorize("hasRole('admin')")
	public String forAdmin() {
		return "This URL is only accessible for Admin";
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('user')")
	public String forUser() {
		return "This URL is only accessible for User";
	}
}
