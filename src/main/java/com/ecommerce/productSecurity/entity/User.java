package com.ecommerce.productSecurity.entity;

import java.util.Set;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Builder
@Setter
@Getter
@ToString
@NoArgsConstructor
public class User {

	@Id
	private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String email;
	@ManyToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL)
	@JoinTable(name="USER_ROLE",
		joinColumns = {
				@JoinColumn(name = "USER_ID")
		},
		inverseJoinColumns = {
				@JoinColumn(name = "ROLE_ID")
		})
	private Set<Role> roles;
	public User(String userName, String firstName, String lastName, String password, String email, Set<Role> roles) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}
	
	
}
