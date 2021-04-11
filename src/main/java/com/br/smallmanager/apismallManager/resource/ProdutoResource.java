package com.br.smallmanager.apismallManager.resource;
 
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
import org.springframework.web.multipart.MultipartFile;

import com.br.smallmanager.apismallManager.dto.ProdutoDTO; 
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.ProdutoService;
import com.br.smallmanager.apismallManager.service.EmpresaService;
import com.br.smallmanager.apismallManager.utils.FotoUploadDisco;

@RestController
@RequestMapping("api/empresa")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;
	@Autowired
	private EmpresaService userService;
	 
	@PostMapping
	public ResponseEntity<?> cadastrarProduto ( @RequestBody ProdutoDTO dto) {
		
		Empresa e = new Empresa ();
		usuario.setId(dto.getEmpresa());
		
		Produto empresa = Produto.builder()
				.nome(dto.getNome())
				.categoria(dto.getCategoria())
				.cnpj(dto.getCnpj())
				.bio_empresa(dto.getBio_empresa())
				.status_empresa(true)
				.data_atualizacao(LocalDate.now())
				.data_cadastro(LocalDate.now())
				.orcamento_cli(dto.getOrcamento_cli())
				.agendamento_cli(dto.getAgendamento_cli())
				.build();
		try {
			Produto empresaSalva = service.cadastrarProduto(empresa,usuario);
			return new ResponseEntity<Produto>(empresaSalva, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateProduto ( @RequestBody ProdutoDTO dto) {
		 if(dto == null) 
				return ResponseEntity.badRequest().body("Não foi possivel atualizar a empresa");
		else {
		
			return service.obterPorId(dto.getId()).map(
					  entity ->{
						try {
							 Empresa usuario = new Empresa ();
							 usuario.setId(dto.getEmpresa());
							  
							 if (userService.obterPorId(usuario.getId()).isPresent()) {
									Produto empresa = Produto.builder()
											
										  	.nome(dto.getNome())
											.categoria(dto.getCategoria())
											.cnpj(dto.getCnpj())
											.bio_empresa(dto.getBio_empresa())
											.status_empresa(true)
											.data_cadastro(entity.getData_cadastro())
											.data_atualizacao(LocalDate.now())
											.orcamento_cli(dto.getOrcamento_cli())
											.agendamento_cli(dto.getAgendamento_cli())
											.usuario(usuario)
											.build();
								  
								  entity = empresa;
								  
								  service.alterarProduto(empresa);
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
				return ResponseEntity.badRequest().body("Não foi possivel localizar empresa");
			}
			else{
				return service.obterPorId(dto.getId()).map(
						  entity ->{
							try { 
								  Produto empresa = Produto.builder()
											.id(dto.getId())
											.build();  
											 
								  entity = empresa;
								  
								  service.excluirProduto(empresa);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Empresa não encontrado.",HttpStatus.BAD_REQUEST));
			}  		 
	}
	
	
	@GetMapping("/buscar")
	public ResponseEntity<?> buscar(
								 @RequestParam(value = "categoria" ,required = false) String categoria,
			 					 @RequestParam(value = "nome",required = false) String nome,
			 					 @RequestParam(value = "cnpj",required = false) String cnpj,
			 					 @RequestParam( "usuario") Long idEmpresa
			 					 
				) {
		
		
		Produto empresaFiltro = new Produto();
		empresaFiltro.setCategoria(categoria);
		empresaFiltro.setNome(nome);
		empresaFiltro.setCnpj(cnpj);
		  
		 Optional<Empresa> user = userService.obterPorId(idEmpresa);
		 
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Usuário não encontrado.");
		 else
			 empresaFiltro.setEmpresa(user.get());
		 	
		 List<Produto> lancamentos = service.buscar(empresaFiltro);
		 
		 
		return ResponseEntity.ok(lancamentos);
		
	}
 
	@GetMapping("/buscar/{id_produto}/{id_empresa}")
	public ResponseEntity<?> buscar( @PathVariable( "id_produto") Long idProduto, @PathVariable( "id_empresa") Long idEmpresa) {
		
		 Produto empFiltro = new Produto();
		 
		 Optional<Empresa> user = userService.obterPorId(idEmpresa);
		  
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Empresa não encontrada.");
		 else
			 empFiltro.setId(idProduto);
		 	 empFiltro.setEmpresa(user.get());
		 	
		 Optional<Produto> empresa = service.buscarPorId(empFiltro);
		  
		return ResponseEntity.ok(empresa.get());
		
	}
	
	 
	 
}
