package com.ecommerce.productSecurity.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ecommerce.productSecurity.entity.User;

@Repository
public interface UserDAO extends CrudRepository<User, String>{

}
