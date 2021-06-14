package com.br.smallmanager.apissmallManager.security; 
import java.util.ArrayList;
import java.util.List;   
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority; 
import com.br.smallmanager.apismallManager.entity.Usuario;

public class JwtUserFactory {

private JwtUserFactory () {
		
}
	
public static JwtUser create(Usuario usuario ) {
 
	return new JwtUser(usuario.getId().toString() ,usuario.getEmail(),usuario.getSenha(), mapToGrantedAuthoriries(usuario.getTipo_usuario()));
}

private static List<GrantedAuthority> mapToGrantedAuthoriries(String profileEnum){
	List<GrantedAuthority> authories = new ArrayList<GrantedAuthority>();
	authories.add(new SimpleGrantedAuthority(profileEnum.toString()));
	
	return authories;
}
}
