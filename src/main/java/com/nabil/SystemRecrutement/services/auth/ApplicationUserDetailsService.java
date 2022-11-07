package com.nabil.SystemRecrutement.services.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nabil.SystemRecrutement.dto.utilisateurDto;
import com.nabil.SystemRecrutement.model.auth.ExtendedUser;
import com.nabil.SystemRecrutement.service.UtilisateurService;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
	
	

	  @Autowired
	  private UtilisateurService service;


	  @Override
	  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
	    utilisateurDto utilisateur = service.findByEmail(email);

	    List<SimpleGrantedAuthority> authorities = new ArrayList<>();
	    utilisateur.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getRoleName())));

	    return new ExtendedUser(utilisateur.getEmail(), utilisateur.getMoteDePasse() , authorities);
	  }

}
