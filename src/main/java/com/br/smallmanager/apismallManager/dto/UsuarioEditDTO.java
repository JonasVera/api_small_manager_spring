package com.br.smallmanager.apismallManager.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
 

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioEditDTO {
	 
	private Long id;	 
	private String nome; 	 
	private String sobrenome;	 
	private Date data_nascimento;	 
	private String sexo;	 
	private String bio;	 
	private String contato_pessoal; 	 
	private String img_login;  
	private String  email;	 
	private boolean confirma_email;	 
	private Date data_cadastro; 	 
	private String senha;
	  
}
