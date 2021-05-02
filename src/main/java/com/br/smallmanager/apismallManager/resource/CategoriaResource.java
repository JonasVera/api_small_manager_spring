package com.br.smallmanager.apismallManager.resource;
  
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

import com.br.smallmanager.apismallManager.constants.Status;
import com.br.smallmanager.apismallManager.dto.CategoriaDTO; 
import com.br.smallmanager.apismallManager.entity.CategoriaProduto; 
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.CategoriaService;
 
@RestController
@RequestMapping("api/categoria")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;
	@Autowired
	private CategoriaService categService;
	 
	@PostMapping
	public ResponseEntity<?> cadastrarCategoria ( @RequestBody CategoriaDTO dto) {
		 
		CategoriaProduto categoria = CategoriaProduto.builder()
				.nome(dto.getNome())
				.descricao(dto.getDescricao()) 
				.status(Status.ATIVO.toString())
				.build();
		try {
			CategoriaProduto categoriaSalva = service.cadastrarCategoria(categoria);
			return new ResponseEntity<CategoriaProduto>(categoriaSalva, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateCategoria (@RequestBody CategoriaDTO dto) {
		 if(dto == null || dto.getId() == null || dto.getId().toString() == " " ) 
				return ResponseEntity.badRequest().body("Não foi possivel atualizar a categoria");
		else {
		
			return service.obterPorId(dto.getId()).map(
					  entity ->{
						try {
							 CategoriaProduto categoria = new CategoriaProduto ();
							 categoria.setId(dto.getId());
							  
							 if (categService.obterPorId(categoria.getId()).isPresent()) {
											CategoriaProduto categoriaUpdate = CategoriaProduto.builder() 
										  	.nome(dto.getNome())
											.descricao(dto.getDescricao())
											.status(dto.getStatus())
											.id(dto.getId())
											.build(); 
								  entity = categoriaUpdate;
								  
								  service.alterarCategoria(categoriaUpdate);
							 }else {
								 return new ResponseEntity<String>("Categoria não entrada.",HttpStatus.BAD_REQUEST);
							 } 
							  return ResponseEntity.ok(entity);
						} catch (RegraNegocioException e ) {
							return ResponseEntity.badRequest().body(e.getMessage());
						} 
				 
					}).orElseGet(() 
					-> new ResponseEntity<String>("Categoria não encontrada.",HttpStatus.BAD_REQUEST));
		}   
	}
	 
	@DeleteMapping("/excluirCategoria/{id_categoria}")
	public ResponseEntity<?>  deleteCategoria ( @PathVariable( "id_categoria") Long idCategoria) {
		 
		  if(idCategoria == null){
				return ResponseEntity.badRequest().body("Não foi possivel localizar categoria");
			}
			else{
				return service.obterPorId(idCategoria).map(
						  entity ->{
							try { 
								  CategoriaProduto categoria = CategoriaProduto.builder()
											.id(idCategoria)
											.build();   
								  entity = categoria; 
								  service.excluirCategoria(categoria);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Categoria não encontrada.",HttpStatus.BAD_REQUEST));
			}  		 
	} 
	@GetMapping("/buscar")
	public ResponseEntity<?> buscar(
								 @RequestParam(value = "categoria" ,required = false) String categoria,
			 					 @RequestParam(value = "descricao",required = false) String descricao, 
			 					 @RequestParam(value = "idCategoria",required = false) Long idCategoria
				) {

			CategoriaProduto categoriaFiltro = new CategoriaProduto();
			categoriaFiltro.setNome(categoria);
			categoriaFiltro.setDescricao(descricao);
			    
			 List<CategoriaProduto> lancamentos = service.buscar(categoriaFiltro);
			  
			return ResponseEntity.ok(lancamentos);
		
	}
 
	@GetMapping("/buscar/{id_categoria}")
	public ResponseEntity<?> buscar( @PathVariable( "id_categoria") Long idCategoria) {
		
		 CategoriaProduto empFiltro = new CategoriaProduto();
		 
		 Optional<CategoriaProduto> user = categService.obterPorId(idCategoria);
		  
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Categoria não encontrada.");
		 else
			 empFiltro.setId(idCategoria);
		 	  
		 Optional<CategoriaProduto> categoria = service.buscarPorId(empFiltro);
		  
		return ResponseEntity.ok(categoria.get());
		
	}


}
