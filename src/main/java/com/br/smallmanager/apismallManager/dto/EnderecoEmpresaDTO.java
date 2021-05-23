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
public class EnderecoEmpresaDTO {
 
	private Long id;
	 
	private String cidade; 
 
	private String uf;
 
	private String logradouro;
	 
	private String numero;
	 
	private String cep;
	
	private Long empresa;
	 
}
