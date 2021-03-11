package com.br.smallmanager.apismallManager.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Table(name = "usuario", schema = "api_smallmanager")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
 
	@Id
	@Column(name = "id_usuario")
	@GeneratedValue(strategy = GenerationType.IDENTITY)	 
	private Long id_usuario;
	@Column(name = "nome")
	private String nome; 
	@Column(name = "sobrenome")
	private String sobrenome;
	@Column(name = "data_nascimento")
	private Date data_nacimento;
	@Column(name = "sexo")
	private String sexo;
	@Column(name = "bio")
	private String bio;
	@Column(name = "contato_pessoal")
	private String contato_pessoal; 
	@Column(name = "img_login")
	private String img_login;
	@Column(name = "status_perfil")
	private String status_perfil;
	@Column(name = "email")
	private String  email;
	@Column(name = "confirma_email")
	private boolean confirma_email;
	@Column(name = "data_cadastro")
	private Date data_cadastro; 
	@Column(name = "data_atualizacao")
	private Date data_atualizacao;
	
	
}
