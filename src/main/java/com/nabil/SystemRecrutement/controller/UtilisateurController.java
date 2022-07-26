package com.nabil.SystemRecrutement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.nabil.SystemRecrutement.controller.api.UtilisateurApi;
import com.nabil.SystemRecrutement.dto.ChangerMotDePasseUtilisateurDto;
import com.nabil.SystemRecrutement.dto.utilisateurDto;
import com.nabil.SystemRecrutement.service.UtilisateurService;

@RestController
public class UtilisateurController implements UtilisateurApi {

	  private UtilisateurService utilisateurService;

	  @Autowired
	  public UtilisateurController(UtilisateurService utilisateurService) {
	    this.utilisateurService = utilisateurService;
	  }

	  @Override
	  public utilisateurDto save(utilisateurDto dto) {
	    return utilisateurService.save(dto);
	  }

	  @Override
	  public utilisateurDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
	    return utilisateurService.changerMotDePasse(dto);
	  }

	  @Override
	  public utilisateurDto findById(Long id) {
	    return utilisateurService.findById(id);
	  }

	  @Override
	  public utilisateurDto findByEmail(String email) {
	    return utilisateurService.findByEmail(email);
	  }

	  @Override
	  public List<utilisateurDto> findAll() {
	    return utilisateurService.findAll();
	  }

	  @Override
	  public void delete(Long id) {
	    utilisateurService.delete(id);
	  }
	
	
	
}
