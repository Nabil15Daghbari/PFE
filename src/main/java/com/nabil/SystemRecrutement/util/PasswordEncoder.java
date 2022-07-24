package com.nabil.SystemRecrutement.util;


 import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Classe permettant de créer des mots de passe hachés pour des besoins de tests 
 *et d'initialisation de la base de données
 *
 */


public class PasswordEncoder {

	private static BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		bCryptPasswordEncoder = new BCryptPasswordEncoder();
		String password ="login3";
		String encodedPassword = bCryptPasswordEncoder.encode(password);
		System.out.println("Mot de passe en clair : "+password);
		System.out.println("Mot de passe haché : "+encodedPassword);
		//Pour vérifier que le mot de passe haché correspond bien au mot de passe initial, il utiliser la méthode bCryptPasswordEncoder.matches(x, y)
		System.out.println("Le mot de passe est bien haché : "+bCryptPasswordEncoder.matches(password, encodedPassword));
	}

 }