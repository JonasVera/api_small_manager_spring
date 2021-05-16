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

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "eventos_agenda", schema = "api_smallmanager") 
@Data
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {
	 
	@Id
	@Column(name = "id")	 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@Column(name = "titulo")
	private String titulo; 
	
	@Column(name = "id_google")
	private String id_google;
	
	@Column(name = "descricao")
	private String descricao;
	
	@Column(name = "status")
	private String status; 

	@Column(name = "email_cliente")
	private String email_cliente;
	
	@Column(name = "whatsapp")
	private String whatsapp;
	
	@Column(name = "data_inicio")
	private Date data_inicio;
	
	@Column(name = "hora_inicio")
	private String hora_inicio;
	
 
}
