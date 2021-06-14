package com.br.smallmanager.apismallManager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service; 
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apissmallManager.security.JwtUserFactory;
 
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
	@Autowired
	private UsuarioService userService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		 Usuario usuario = userService.findByEmail(email);
		 if(usuario == null) {
			 throw new UsernameNotFoundException(String.format("Usuário não encontrado com este nome '%s'", email));
		 }else {
			 return JwtUserFactory.create(usuario);
		 } 
	}
	 
}
