package com.nabil.SystemRecrutement.config;
 

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nabil.SystemRecrutement.filters.JwtAuthenticationFilters;
import com.nabil.SystemRecrutement.filters.JwtAuthorizationFilter;
import com.nabil.SystemRecrutement.model.AppUser;
import com.nabil.SystemRecrutement.service.AccountService;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private AccountService accountService ;
	
	@Override 
	protected void  configure(AuthenticationManagerBuilder auth) throws Exception{
		
	auth.userDetailsService(new UserDetailsService() {
		
		@Override
		public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
			AppUser appUser = accountService.loadUserByUsername(username);
			
			Collection<GrantedAuthority> authorities = new ArrayList<>();
			appUser.getAppRoles().forEach(r->{
				authorities.add(new SimpleGrantedAuthority(r.getRoleName()));
			});
			
			return new User(appUser.getUsername(), appUser.getPassword() , authorities );
		}
	});
		
	
	}
	
	
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	
		http.csrf().disable() ;
		http.authorizeRequests().antMatchers( "/swagger-resources",
				"/swagger-resources/**",
				"/configuration/ui",
				"/configuration/security",
				"/swagger-ui/index.html",
				"/webjars/**",
				"/v3/api-docs/**",
				"/swagger-ui/**"
				
				).permitAll();
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.headers().frameOptions().disable() ;
		//http.formLogin();
	//	http.authorizeRequests().anyRequest().authenticated();
		http.addFilter(new JwtAuthenticationFilters(authenticationManagerBean()) );
		http.addFilterBefore(new JwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	
	    @Bean
		@Override
		public AuthenticationManager authenticationManagerBean() throws Exception {
			// TODO Auto-generated method stub
			return super.authenticationManagerBean();
		}
	 

	
}

