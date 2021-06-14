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
	 
	private Long id;
	 
	private Double preco_venda;
	
	private Double preco_compra;
 
	private String nome;
	 
	private Long categoria;
	 
	private Long empresa;
	 
	private String descricao; 
	 
	private Double estoque_maximo;
	 
	private Double estoque_minimo;
	 
	private Double altura;
	 
	private Double peso;
	 
	private Boolean disponivel_entrega;
	 
	private Boolean status;
}
