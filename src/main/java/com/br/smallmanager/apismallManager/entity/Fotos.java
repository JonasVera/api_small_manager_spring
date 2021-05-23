package com.br.smallmanager.apismallManager.entity;
 
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
 
@Data
@EqualsAndHashCode
@NoArgsConstructor
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
	 
	@Column(name = "contexto")
	private String contexto; 
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	@JsonIgnore
	private Produto produto;
	 
}
