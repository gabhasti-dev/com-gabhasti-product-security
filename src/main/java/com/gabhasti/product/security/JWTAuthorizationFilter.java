package com.gabhasti.product.security;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger log = LogManager.getLogger(JWTAuthorizationFilter.class.getName());

	public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		String header = request.getHeader(SecurityConstants.HEADER_STRING);
		log.debug("Header" + header);
		if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {

			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
		log.debug("Authentication" + authentication);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
	
		if (token != null) {
			final String strippeToken = token.replace(SecurityConstants.TOKEN_PREFIX, "");
			DecodedJWT decodeJwt = JWT.require(Algorithm.HMAC256(SecurityConstants.SECRET.getBytes())).build()
					.verify(strippeToken);
			// parse the token
			
			String user = decodeJwt.getSubject();
			Claim claim = decodeJwt.getClaim(SecurityConstants.AUTHORITIES_KEY);
			final List<?extends GrantedAuthority> authorities = claim.asList(String.class).stream().map(authority -> new SimpleGrantedAuthority((String) authority))
					.collect(Collectors.toList());
			
			if (user != null) {
				log.debug("Creating new UsernamePasswordAuthenticationToken with user"+user+ " and Authorities"+authorities.toString());
				return new UsernamePasswordAuthenticationToken(user, "", authorities);
			}
			return null;
		}
		return null;
	}

}
