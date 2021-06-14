package com.br.smallmanager.apismallManager.config;
 
import com.br.smallmanager.apismallManager.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrenteUser {

	private String token;
	private Usuario usuario;
	
	 
}
