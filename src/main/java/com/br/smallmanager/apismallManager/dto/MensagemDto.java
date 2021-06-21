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
public class MensagemDto {
	
	private String nome;
	private String email;
	private String assunto;
	private String mensagem;
	private Long empresa;
	private String logo;
	private String tipo;
	private String hora;
	private boolean lido;
}
