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
public class AgendamentoDTO {
	private Long id; 
	private Long empresa; 
	private String titulo;  
	private String id_google; 
	private String descricao; 
	private String status;  
	private String email; 
	private String whatsapp; 
	private String dataInicio; 
	private String horaInicio;
}
