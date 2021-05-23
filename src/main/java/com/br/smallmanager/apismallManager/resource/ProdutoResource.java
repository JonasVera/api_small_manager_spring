package com.br.smallmanager.apismallManager.resource;
 
import java.math.BigDecimal;
import java.time.Instant; 
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.smallmanager.apismallManager.constants.Status;
import com.br.smallmanager.apismallManager.dto.ProdutoDTO; 
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.entity.CategoriaProduto;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.ProdutoService;
import com.br.smallmanager.apismallManager.service.CategoriaService;
 

@RestController
@RequestMapping("api/produto")
@CrossOrigin(origins = "*")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	@Autowired
	private CategoriaService userService;
	 
	@PostMapping
	public ResponseEntity<?> cadastrarProduto ( @RequestBody ProdutoDTO dto) {
		
		CategoriaProduto categoria = new CategoriaProduto ();
		categoria.setId(dto.getCategoria());
		
		Empresa empresa = new Empresa ();
		empresa.setId(dto.getEmpresa());
	 
		int qtdEncontrado = service.obterPorNome(dto.getNome()).size();
		if (qtdEncontrado != 0){
			return new ResponseEntity<String>("Produto já existe",HttpStatus.BAD_REQUEST);
		}else {
			Produto produto = Produto.builder()
					.nome(dto.getNome())
					.categoriaProduto(categoria)
					.empresa(empresa) 
					.descricao(dto.getDescricao())		
					.status(Status.ATIVO.toString())
					.estoque_maximo(BigDecimal.valueOf(dto.getEstoque_maximo()))
					.estoque_minimo(BigDecimal.valueOf(dto.getEstoque_minimo()))
					.peso(BigDecimal.valueOf(dto.getPeso()))
					.altura(BigDecimal.valueOf(dto.getAltura()))
					.categoriaProduto(categoria)                                                                     
					.preco_compra(BigDecimal.valueOf(dto.getPreco_compra()))
					.preco_venda(BigDecimal.valueOf(dto.getPreco_venda()))
					.empresa(empresa)
					.disponivel_entrega(dto.getDisponivel_entrega())
					.data_atualizacao(Instant.now())
					.data_cadastro(Instant.now())
					.build();
			try {
				 Produto produtoSalva = service.cadastrarProduto(produto);
				return new ResponseEntity<Produto>(produtoSalva, HttpStatus.CREATED);
			}catch (RegraNegocioException e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}
			 
	}
	
	@PutMapping
	public ResponseEntity<?> updateProduto ( @RequestBody ProdutoDTO dto) {
		 if(dto == null) 
				return ResponseEntity.badRequest().body("Não foi possivel atualizar a produto");
		else {
		
			return service.obterPorId(dto.getId()).map(
					  entity ->{
						try {
							 CategoriaProduto categoria = new CategoriaProduto ();
							 categoria.setId(dto.getCategoria());
							 Empresa empresa = new Empresa ();
								empresa.setId(dto.getCategoria());
							 if (userService.obterPorId(categoria.getId()).isPresent()) {
									Produto produto = Produto.builder()
											.id(dto.getId())
											.nome(dto.getNome())
											.categoriaProduto(categoria)
											.empresa(empresa)
											.status(Status.ATIVO.toString())
											.descricao(dto.getDescricao())		
											.estoque_maximo(BigDecimal.valueOf(dto.getEstoque_maximo()))
											.estoque_minimo(BigDecimal.valueOf(dto.getEstoque_minimo()))
											.peso(BigDecimal.valueOf(dto.getPeso()))
											.altura(BigDecimal.valueOf(dto.getAltura()))
											.categoriaProduto(categoria)                                                                     
											.preco_compra(BigDecimal.valueOf(dto.getPreco_compra()))
											.preco_venda(BigDecimal.valueOf(dto.getPreco_venda()))
											.empresa(empresa)
											.disponivel_entrega(dto.getDisponivel_entrega())
											.data_atualizacao(Instant.now()) 
											.build();
								  
								  entity = produto;
								  
								  service.alterarProduto(produto);
							 }else {
								 return new ResponseEntity<String>("Produto não entrado.",HttpStatus.BAD_REQUEST);
							 } 
							  return ResponseEntity.ok(entity);
						} catch (RegraNegocioException e ) {
							return ResponseEntity.badRequest().body(e.getMessage());
						} 
				 
					}).orElseGet(() 
					-> new ResponseEntity<String>("Produto não encontrado.",HttpStatus.BAD_REQUEST));
		}
			  
	}

	@DeleteMapping("/excluir/{id_produto}")
	public ResponseEntity<?>  deleteProduto ( @PathVariable( "id_produto") Long id_produto) {
		 
		if(id_produto == null){
            return ResponseEntity.badRequest().body("Não foi possivel localizar categoria");
        }
			else{
				return service.obterPorId(id_produto).map(
						  entity ->{
							try { 
								  Produto produto = Produto.builder()
											.id(id_produto)
											.build();  
											 
								  entity = produto;
								  
								  service.excluirProduto(produto);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Produto não encontrado.",HttpStatus.BAD_REQUEST));
			}  		 
	}
	
	@PutMapping("/ativarDesativarProduto/{id_produto}")
	public ResponseEntity<?>  ativarDesativaProduto ( @PathVariable( "id_produto") Long id_produto) {
		 
		if(id_produto == null){
            return ResponseEntity.badRequest().body("Não foi possivel localizar o produto");
        }
			else{
				return service.obterPorId(id_produto).map(
						  entity ->{
							try {  
								  if (entity.getStatus().equals(Status.ATIVO.toString()))
									  service.ativarDesativar(Status.DESATIVADO,id_produto);
								  else
									  service.ativarDesativar(Status.ATIVO,id_produto);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Produto não encontrado.",HttpStatus.BAD_REQUEST));
			}  		 
	}
	
	@GetMapping
	public List<Produto> listarUsuario(){
		return service.listProdutos();
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<?> buscar(
								 @RequestParam(value = "categoria" ,required = false) String categoria,
			 					 @RequestParam(value = "nome",required = false) String nome, 
			 					 @RequestParam( "categoria") Long idCategoria
			 					 
				) {
		
		
		Produto produtoFiltro = new Produto();
		CategoriaProduto categoFiltro = new CategoriaProduto();
		categoFiltro.setId(idCategoria);
		categoFiltro.setNome(categoria);
		produtoFiltro.setCategoriaProduto(categoFiltro);
		produtoFiltro.setNome(nome);
 
		 Optional<CategoriaProduto> user = userService.obterPorId(idCategoria);
		 
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Categoria não encontrada.");
		 else
			 produtoFiltro.setCategoriaProduto(user.get());
		 	
		 List<Produto> lancamentos = service.buscar(produtoFiltro);
		  
		return ResponseEntity.ok(lancamentos);
		
	}
 
	@GetMapping("/produtoCategoria/{id_categoria}")
	public ResponseEntity<?> buscar( @PathVariable( "id_categoria") Long idCategoria) {
	  
		 Optional<CategoriaProduto> catFiltro = userService.obterPorId(idCategoria);
		  
		 if(!catFiltro.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Categoria não encontrada.");
		 else
		 
		return ResponseEntity.ok(service.obterPorCategoria(catFiltro.get()));
		
	}
	 
}
