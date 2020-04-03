package com.gabhasti.product.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gabhasti.product.beans.ApplicationUser;
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager = null;
	private static final Logger log = LogManager.getLogger(JwtAuthenticationFilter.class.getName());

	public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		try {
			
			ApplicationUser creds = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
			log.debug("verify creds"+creds);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(),new ArrayList<>()));
			
		}catch(IOException ex) {
			throw new RuntimeException(ex);
		}
		
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		//.HMAC256(secret)
		log.debug("generating token");
		String token = JWT.create()
						.withSubject(((User)auth.getPrincipal()).getUsername())
						.withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstants.EXPIRATION_TIME))
						.sign(Algorithm.HMAC256(SecurityConstants.SECRET.getBytes()));
		log.debug("Token::"+token);
		response.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX+token);
		
	}

}
