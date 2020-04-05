package com.gabhasti.product.controller;

import java.security.Principal;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gabhasti.product.beans.ApplicationRoles;
import com.gabhasti.product.beans.ApplicationUser;
import com.gabhasti.product.repository.ApplicationUserRepository;

@RestController
@RequestMapping("/security")
public class UserController {

	private static final Logger log = LogManager.getLogger(UserController.class.getName());
	
	private ApplicationUserRepository applicationUserRepository=null;
	private PasswordEncoder passwordEncoder=null ;
	public UserController(ApplicationUserRepository applicationUserRepository,PasswordEncoder passwordEncoder) {
		
		this.applicationUserRepository=applicationUserRepository;
		this.passwordEncoder=passwordEncoder;
		
	}
	
	//@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/user/sign-up")
	public void signUp(@RequestBody ApplicationUser user) {
		log.debug("User Registeration ::"+user.getUsername());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		applicationUserRepository.save(user);
		log.debug("User Registeration complete::"+user.getUsername());
		
	}
	@GetMapping("/user/roles")
	public ResponseEntity<List<ApplicationRoles>> getAllUsersRoles(Principal principal){
		ApplicationUser user =applicationUserRepository.findByUsername(principal.getName());
		
		return new ResponseEntity(user.getRoles(),HttpStatus.OK);
		
	}
	
	@GetMapping("/user")
	public ResponseEntity<List<ApplicationUser>> getAllUsers(Principal principal){
		ApplicationUser user =applicationUserRepository.findByUsername(principal.getName());
		//user.setPassword(null);
		return new ResponseEntity(user,HttpStatus.OK);
		
	}
	

	
	
	
	
	@GetMapping("/user/principal")
	public ResponseEntity<Principal> getPrincipal(Principal principal){
		
		return new ResponseEntity(principal,HttpStatus.OK);
		
	}
}
