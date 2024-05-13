package com.ecommerce.productSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.productSecurity.dao.UserDAO;
import com.ecommerce.productSecurity.entity.JwtRequest;
import com.ecommerce.productSecurity.entity.JwtResponse;
import com.ecommerce.productSecurity.entity.User;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

	private final UserDAO dao;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	
	public AuthenticationServiceImpl(UserDAO dao, AuthenticationManager authenticationManager, JwtService jwtService) {
		this.dao = dao;
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
	}

	@Override
	public JwtResponse createJwtToken(JwtRequest request) {
		authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getUserPassword()));
		String newGeneratedToken = jwtService.generateToken(request.getUserName());
		User user = dao.findById(request.getUserName()).get();
	    return new JwtResponse(user, newGeneratedToken);
	}

}
