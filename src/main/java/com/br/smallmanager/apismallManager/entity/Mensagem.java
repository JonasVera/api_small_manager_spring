package com.br.smallmanager.apismallManager.entity;
  
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table; 
import com.fasterxml.jackson.annotation.JsonIgnore; 
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
@Entity
@Table(name = "contato_cliente", schema = "api_smallmanager")
public class Mensagem {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "assunto")
	private String assunto;
	
	@Column(name = "mensagem")
	private String mensagem;
	
	@Column(name = "data_envio")
	private Date dataEnvio;
	
	@Column(name = "tipo")
	private String tipo;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	@JsonIgnore
	private Empresa empresa;
 
}
