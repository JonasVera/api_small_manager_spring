package com.br.smallmanager.apismallManager.entity;

import java.time.LocalDate;  
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
@Entity
@Table(name = "empresa", schema = "api_smallmanager")
 
@Data
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {
	@Id
	@Column(name = "id")	 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario") 
	private Usuario usuario;
	
	@Column(name = "categoria")
	private String categoria;
	@Column(name = "nome")
	private String  nome;
	@Column(name = "cnpj")
	private String  cnpj;
	@Column(name = "bio_empresa")
	private String  bio_empresa ;
	@Column(name = "img_logotipo")
	private String  img_logotipo ;
	@Column(name = "status_empresa")
	private  boolean status_empresa ; 
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "data_cadastro")
	private LocalDate data_cadastro;
	
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@Column(name = "data_atualizacao")
	private  LocalDate data_atualizacao;
	
	@Column(name = "agendamento_cli")
	private Boolean agendamento_cli;
	
	@Column(name = "orcamento_cli")
	private Boolean orcamento_cli;
	
	
     @OneToMany(mappedBy = "empresa") 
	 private List<Contato> contatos;
     
     @OneToMany(mappedBy = "empresa")
	 private List<EnderecoEmpresa> enderecoEmpresa;
     
     @OneToMany(mappedBy = "empresa") 
	 private List<Produto> produto;
}
