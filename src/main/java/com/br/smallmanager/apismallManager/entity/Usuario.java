package com.br.smallmanager.apismallManager.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table; 
import com.br.smallmanager.apismallManager.dto.UsuarioStartDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
 
import lombok.NoArgsConstructor;
 
import lombok.ToString;
@Entity
@Table(name = "usuario", schema = "api_smallmanager")
 
@Data
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {
 
	@Id
	@Column(name = "id")	 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome; 
	
	@Column(name = "sobrenome")
	private String sobrenome;
	
	@Column(name = "data_nascimento")
	private Date data_nascimento;
	
	@Column(name = "sexo")
	private String sexo;
	
	@Column(name = "bio")
	private String bio;
	
	@Column(name = "contato_pessoal")
	private String contato_pessoal; 
	
	@Column(name = "img_login")
	private String img_login;
	
	@Column(name = "status_perfil")
	private Boolean status_perfil;
	
	@Column(name = "email")
	private String  email;
	
	@Column(name = "confirma_email")
	private Boolean confirma_email;
	
	@Column(name = "data_cadastro")
	private Date data_cadastro; 
	
	@Column(name = "data_atualizacao")
	private Date data_atualizacao;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "codigo_confirmacao")
	private String codigo_confirmacao;
	
	@Column(name = "tipo_usuario")
	private String tipo_usuario;
	 
	
	public void DtoStartUsuarioToUsuario(UsuarioStartDTO usuario) {
		setNome(usuario.getNome());
		setEmail(usuario.getEmail());
		setSenha(usuario.getSenha()); 
	}
	  
	@OneToMany(mappedBy="usuario")
	@JsonIgnore
    private List<Empresa> empresa;
}
