package com.ecommerce.productSecurity.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.productSecurity.dao.RoleDAO;
import com.ecommerce.productSecurity.dao.UserDAO;
import com.ecommerce.productSecurity.entity.Role;
import com.ecommerce.productSecurity.entity.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO dao;
	
	@Autowired
	private RoleDAO roleDAO;
	
	@Override
	public User registerUser(User user) {
		if (user.getRoles() == null) {
			Role role = roleDAO.findById("user").get();
			Set<Role> roleSet = new HashSet<>();
			roleSet.add(role);
			user.setRoles(roleSet);
		}
		
		return dao.save(user);
	}

}
