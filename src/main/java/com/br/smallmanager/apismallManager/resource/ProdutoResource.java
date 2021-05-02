package com.br.smallmanager.apismallManager.resource;
 
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController; 
import com.br.smallmanager.apismallManager.dto.ProdutoDTO; 
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.entity.CategoriaProduto;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.ProdutoService;
import com.br.smallmanager.apismallManager.service.CategoriaService;
import com.br.smallmanager.apismallManager.utils.FotoUploadDisco;

@RestController
@RequestMapping("api/produto")
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
		
		Produto produto = Produto.builder()
				.nome(dto.getNome())
				.categoria(categoria)
				.empresa(empresa) 
				.descricao(dto.getDescricao())		
				.estoque_maximo(BigDecimal.valueOf(dto.getEstoque_maximo()))
				.estoque_minimo(BigDecimal.valueOf(dto.getEstoque_minimo()))
				.peso(BigDecimal.valueOf(dto.getPeso()))
				.altura(BigDecimal.valueOf(dto.getAltura()))
				.categoria(categoria)
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
											
											.nome(dto.getNome())
											.categoria(categoria)
											.empresa(empresa) 
											.descricao(dto.getDescricao())		
											.estoque_maximo(BigDecimal.valueOf(dto.getEstoque_maximo()))
											.estoque_minimo(BigDecimal.valueOf(dto.getEstoque_minimo()))
											.peso(BigDecimal.valueOf(dto.getPeso()))
											.altura(BigDecimal.valueOf(dto.getAltura()))
											.disponivel_entrega(dto.getDisponivel_entrega())
											.build();
								  
								  entity = produto;
								  
								  service.alterarProduto(produto);
							 }else {
								 return new ResponseEntity<String>("Usuário não entrado.",HttpStatus.BAD_REQUEST);
							 } 
							  return ResponseEntity.ok(entity);
						} catch (RegraNegocioException e ) {
							return ResponseEntity.badRequest().body(e.getMessage());
						} 
				 
					}).orElseGet(() 
					-> new ResponseEntity<String>("Produto não encontrado.",HttpStatus.BAD_REQUEST));
		}
			  
	}

	@DeleteMapping("/excluirProduto")
	public ResponseEntity<?>  deleteProduto ( @RequestBody ProdutoDTO dto) {
		 
		  if(dto == null){
				return ResponseEntity.badRequest().body("Não foi possivel localizar produto");
			}
			else{
				return service.obterPorId(dto.getId()).map(
						  entity ->{
							try { 
								  Produto produto = Produto.builder()
											.id(dto.getId())
											.build();  
											 
								  entity = produto;
								  
								  service.excluirProduto(produto);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Categoria não encontrado.",HttpStatus.BAD_REQUEST));
			}  		 
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
		produtoFiltro.setCategoria(categoFiltro);
		produtoFiltro.setNome(nome);
 
		 Optional<CategoriaProduto> user = userService.obterPorId(idCategoria);
		 
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Categoria não encontrada.");
		 else
			 produtoFiltro.setCategoria(user.get());
		 	
		 List<Produto> lancamentos = service.buscar(produtoFiltro);
		  
		return ResponseEntity.ok(lancamentos);
		
	}
 
	@GetMapping("/buscar/{id_categoria}/{id_produto}")
	public ResponseEntity<?> buscar( @PathVariable( "id_categoria") Long idCategoria, @PathVariable( "id_produto") Long idProduto) {
		
		 Produto empFiltro = new Produto();
		 
		 Optional<CategoriaProduto> user = userService.obterPorId(idCategoria);
		  
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Categoria não encontrada.");
		 else
			 empFiltro.setId(idProduto);
		 	 empFiltro.setCategoria(user.get());
		 	
		 Optional<Produto> produto = service.buscarPorId(empFiltro);
		  
		return ResponseEntity.ok(produto.get());
		
	}
	 
}
