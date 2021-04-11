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
public class EmpresaDTO {

 
	private Long id;
	 
	private Long usuario;
 
	private String categoria;
	 
	private String  nome;
	 
	private String  cnpj;
 
	private String  bio_empresa;
	 
	private Boolean  img_logotipo;
 
	private String status_empresa; 
    
	private Boolean agendamento_cli;
	
	private Boolean orcamento_cli;
	 
}
