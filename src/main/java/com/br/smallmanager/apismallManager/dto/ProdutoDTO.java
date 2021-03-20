package com.br.smallmanager.apismallManager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProdutoDTO {
	 
	private String id;
	 
	private String nome;
	 
	private String categoria;
	 
	private String empresa;
	 
	private String descricao; 
	 
	private String estoque_maximo;
	 
	private String estoque_minimo;
	 
	private String altura;
	 
	private String peso;
	 
	private String disponivel_entrega;
	 
	private String status;
}
