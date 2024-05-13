package com.ecommerce.productSecurity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.productSecurity.dao.RoleDAO;
import com.ecommerce.productSecurity.entity.Role;

@Service
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDAO dao;
	
	@Override
	public Role createRole(Role role) {
		return dao.save(role);
	}

}
