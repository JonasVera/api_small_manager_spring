package com.br.smallmanager.apismallManager.entity;

import java.math.BigDecimal;
import java.time.Instant; 
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.br.smallmanager.apismallManager.constants.Status;
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
@Table(name = "produto", schema = "api_smallmanager")
public class Produto {

	@Id
	@Column(name = "id")	 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome; 
	@ManyToOne
	@JoinColumn(name = "empresa_id")
	private Empresa empresa;
	
	@Column(name = "descricao")
	private String descricao;
	 
	@Column(name = "estoque_maximo")
	private BigDecimal estoque_maximo;
	
	@Column(name = "estoque_minimo")
	private BigDecimal estoque_minimo;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private CategoriaProduto categoriaProduto;
	
	
	@Column(name = "altura")
	private BigDecimal altura;
	
	@Column(name = "peso")
	private BigDecimal peso;
	
	@Column(name = "preco_venda")
	private BigDecimal preco_venda;
	
	@Column(name = "preco_compra")
	private BigDecimal preco_compra;
	
	
	@Column(name = "disponivel_entrega")
	private Boolean disponivel_entrega;
	
	@Column(name = "status")
	private String status;
	
	@Column(name = "data_cadastro")
	private Instant data_cadastro;
 
	@Column(name = "data_atualizacao")
	private Instant data_atualizacao;
	 
    @OneToMany(mappedBy = "produto")
    @JsonIgnore 
	private List<Fotos> fotos;
}
