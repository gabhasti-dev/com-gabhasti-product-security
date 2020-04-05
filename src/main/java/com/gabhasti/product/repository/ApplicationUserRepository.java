package com.gabhasti.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gabhasti.product.beans.ApplicationUser;

public interface ApplicationUserRepository extends JpaRepository<ApplicationUser, String> {

	ApplicationUser findByUsername(String username);
	
}
