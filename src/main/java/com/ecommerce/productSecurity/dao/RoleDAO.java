package com.ecommerce.productSecurity.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.productSecurity.entity.Role;

@Repository
public interface RoleDAO extends CrudRepository<Role, String>{

}
