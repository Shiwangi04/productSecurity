package com.ecommerce.productSecurity.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecommerce.productSecurity.dao.UserDAO;
import com.ecommerce.productSecurity.entity.User;
import com.ecommerce.productSecurity.util.JwtUtil;

@Service
public class JwtService implements UserDetailsService{
	

	@Autowired
	private UserDAO dao;
	@Autowired
	private JwtUtil util;
	
	public String generateToken(String username) {
		final UserDetails userdetails = loadUserByUsername(username);
        return util.generateToken(userdetails);
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = dao.findById(username).get();
		if (user != null) {
			return new org.springframework.security.core.userdetails
					.User(user.getUserName(), user.getPassword(), getAuthorities(user));
		} else {
			throw new UsernameNotFoundException("User name is not valid!");
		}
	}
	
	private Set getAuthorities(User user) {
		Set authorities = new HashSet<>();
		
		user.getRoles().forEach(role -> 
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName())));
		
		return authorities;
	}
}
