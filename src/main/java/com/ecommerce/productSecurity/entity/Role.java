package com.ecommerce.productSecurity.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
public class Role {

	@Id
	private String roleName;
	private String roleDescription;
	public Role(String roleName, String roleDescription) {
		this.roleName = roleName;
		this.roleDescription = roleDescription;
	}
	
	
	
}
