package com.nabil.SystemRecrutement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.nabil.SystemRecrutement.controller.api.AuthenticationApi1;
import com.nabil.SystemRecrutement.dto.auth.AuthenticationRequest;
import com.nabil.SystemRecrutement.dto.auth.AuthenticationResponse;
import com.nabil.SystemRecrutement.model.auth.ExtendedUser;
import com.nabil.SystemRecrutement.services.auth.ApplicationUserDetailsService;
import com.nabil.SystemRecrutement.util.JwtUtil;



@RestController
//@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController implements AuthenticationApi1{

	 @Autowired
	  private AuthenticationManager authenticationManager;

	  @Autowired
	  private ApplicationUserDetailsService userDetailsService;

	  @Autowired
	  private JwtUtil jwtUtil;

	  @Override
	  public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
	    authenticationManager.authenticate(
	        new UsernamePasswordAuthenticationToken(
	            request.getLogin(),
	            request.getPassword()
	        )
	    );
	    final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getLogin());

	    final String jwt = jwtUtil.generateToken((ExtendedUser) userDetails);

	    return ResponseEntity.ok(AuthenticationResponse.builder().accessToken(jwt).build());
	  }


}
