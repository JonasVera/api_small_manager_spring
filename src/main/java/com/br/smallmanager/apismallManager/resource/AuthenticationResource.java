package com.br.smallmanager.apismallManager.resource;

import javax.servlet.http.HttpServletRequest; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.br.smallmanager.apismallManager.config.CurrenteUser;
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.service.UsuarioService;
import com.br.smallmanager.apismallManager.utils.JwtTokenUtil;
import com.br.smallmanager.apissmallManager.security.JwtAuthenticationRequest;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder; 
 

@RestController
@CrossOrigin(origins = "*") 
public class AuthenticationResource {

	@Autowired
	private AuthenticationManager authenticationManager;
		 
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	 
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UsuarioService userService;
 
	@PostMapping(value = "/api/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest){
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(),authenticationRequest.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
		 
		final String token = jwtTokenUtil.generateToken(userDetails);
		final Usuario user = userService.findByEmail(authenticationRequest.getEmail());
		user.setSenha(null);
		return ResponseEntity.ok(new CurrenteUser(token,user)); 
	}
	
	@PostMapping(value = "api/refresh/")
	public ResponseEntity<?> refreshAndGetAuthenticationToken(HttpServletRequest request){
   
		  String token = request.getHeader("Authorization");
		  String userName = jwtTokenUtil.getUserFromToken(token);
		  final Usuario user = userService.findByEmail(userName);
		  if (jwtTokenUtil.canTokenBeRefreshed(token)) {
			  String refresdToken = jwtTokenUtil.refreshToken(token);
			  return ResponseEntity.ok(new CurrenteUser(refresdToken,user));
		  }else
			  return ResponseEntity.ok(null); 
	}
	 
}
