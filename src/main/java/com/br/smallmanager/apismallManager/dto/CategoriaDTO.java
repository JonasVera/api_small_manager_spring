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
public class CategoriaDTO {
 
	private Long id; 
	private String nome;  
	private String status; 
	private String descricao;
}
