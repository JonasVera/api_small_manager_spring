package com.br.smallmanager.apismallManager.entity;

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
@Entity
@Table(name = "endereco_empresa", schema = "api_smallmanager") 
@Data
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoEmpresa {
	@Id
	@Column(name = "id")	 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "cidade")
	private String cidade; 
	
	@Column(name = "uf")
	private String uf;

	@Column(name = "logradouro")
	private String logradouro;
	
	@Column(name = "numero")
	private String numero;
	
	@Column(name = "cep")
	private String cep;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	 
}
