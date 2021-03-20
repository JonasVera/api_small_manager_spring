package com.br.smallmanager.apismallManager.entity;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

 
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
 
@Data
@Builder
@EqualsAndHashCode
@Entity
@Table(name = "fotos", schema = "api_smallmanager")
public class Fotos {
	
	@Id
	@Column(name = "id")	 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "nome")
	private String nome;
	@Column(name = "caminho")
	private String caminho;
	@Column(name = "tamanho")
	private String tamanho;
	@Column(name = "usuario")
	private Usuario usuario;
	@Column(name = "contexto")
	private String contexto;
	 
}
