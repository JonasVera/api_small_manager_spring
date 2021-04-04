package com.br.smallmanager.apismallManager.entity;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode
@ToString
@Entity
@Table(name = "produto", schema = "api_smallmanager")
public class Produto {

	@Id
	@Column(name = "id")	 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "categoria")
	private String categoria;
	
	@ManyToOne
	@JoinColumn(name = "empresa")
	private Empresa empresa;
	
	@Column(name = "descricao")
	private String descricao;
	 
	@Column(name = "estoque_maximo")
	private BigDecimal estoque_maximo;
	
	@Column(name = "estoque_minimo")
	private BigDecimal estoque_minimo;
	
	@Column(name = "altura")
	private BigDecimal altura;
	
	@Column(name = "peso")
	private BigDecimal peso;
	
	@Column(name = "disponivel_entrega")
	private Boolean disponivel_entrega;
	
	@Column(name = "status")
	private Boolean status;
	 
}