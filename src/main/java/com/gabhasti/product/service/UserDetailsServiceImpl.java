package com.gabhasti.product.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gabhasti.product.beans.ApplicationUser;
import com.gabhasti.product.repository.ApplicationUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	private static final Logger log = LogManager.getLogger(UserDetailsServiceImpl.class.getName());

	private ApplicationUserRepository repository;
	
	public UserDetailsServiceImpl(ApplicationUserRepository applicationUserRepository) {
		this.repository=applicationUserRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		ApplicationUser user = repository.findByUsername(username);
		log.debug("Loading UserDetails for ::"+username);
		if(user== null) {
			throw new UsernameNotFoundException(username);
		}
		log.debug("Loaded UserDetails for ::"+user.getRoles().toString());
		return new User(user.getUsername(),user.getPassword(),user.getRoles());
		
	}

}
