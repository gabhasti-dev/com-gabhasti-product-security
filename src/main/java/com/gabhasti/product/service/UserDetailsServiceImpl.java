package com.gabhasti.product.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gabhasti.product.beans.ApplicationUser;
import com.gabhasti.product.repository.ApplicationUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private ApplicationUserRepository repository;
	
	public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
		this.repository=applicationUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser user = repository.findByUsername(username);
		
		if(user== null) {
			throw new UsernameNotFoundException(username);
		}
		return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
		
	}

}
