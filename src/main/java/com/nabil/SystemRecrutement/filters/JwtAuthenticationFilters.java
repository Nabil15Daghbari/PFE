package com.nabil.SystemRecrutement.filters;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JwtAuthenticationFilters extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager ;
	
	public JwtAuthenticationFilters(AuthenticationManager authenticationManager) {
	
		this.authenticationManager = authenticationManager;
	}

	
  
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		System.out.println("attemptAuthentication");
		String username =request.getParameter("username");
		String password =request.getParameter("password");
		
		System.out.println(username);
		System.out.println(password);
		
		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(username, password);
		
		return authenticationManager.authenticate(authenticationToken);
	}
	
	///// /// methode qui va génerer le jwtAccessToken
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		
		
		User user=(User) authResult.getPrincipal();
		 Algorithm algo1 =Algorithm.HMAC256("mySecret1234");	 
		
		 
		 
		 String jwtAccessToken=JWT.create()
				 .withSubject(user.getUsername())
				 .withExpiresAt(new Date(System.currentTimeMillis()+11115*60*1000))
				 .withIssuer(request.getRequestURI().toString())
				 .withClaim("roles", user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
				 .sign(algo1);
		 
		 
		 String jwtRefreshToken=JWT.create()
				 .withSubject(user.getUsername())
				 .withExpiresAt(new Date(System.currentTimeMillis()+15*60*1000))
				 .withIssuer(request.getRequestURI().toString())
				 .sign(algo1);
				
		 
		 Map<String , String> idToken=new HashMap<>();
		 idToken.put("access-token", jwtAccessToken);
		 idToken.put("refresh-token", jwtRefreshToken);
		 response.setContentType("application/json");
		 
	//	 new ObjectMapper().writeValue(response.getOutputStream(), idToken);
		 
		 
		 // token atla3 foug fi postman taw telgeha 
			//response.setHeader("Authorization", jwtAccessToken);	 
		//	System.out.println("successfulAuthentication");
				 
				}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}

