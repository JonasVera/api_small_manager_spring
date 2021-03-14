package com.br.smallmanager.apismallManager.resource;
 
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.br.smallmanager.apismallManager.dto.EmpresaDTO;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.EmpresaService;
import com.br.smallmanager.apismallManager.service.UsuarioService;

@RestController
@RequestMapping("api/empresa")
public class EmpresaResource {

	@Autowired
	private EmpresaService service;
	@Autowired
	private UsuarioService userService;
	 
	@PostMapping
	public ResponseEntity<?> cadastrarEmpresa ( @RequestBody EmpresaDTO dto) {
		
		Usuario usuario = new Usuario ();
		usuario.setId(Long.parseLong(dto.getUsuario()));
		
		Empresa empresa = Empresa.builder()
				.nome(dto.getNome())
				.categoria(dto.getCategoria())
				.cnpj(dto.getCnpj())
				.bio_empresa(dto.getBio_empresa())
				.status_empresa(true)
				.data_atualizacao(LocalDate.now())
				.data_cadastro(LocalDate.now())
				.build();
		try {
			Empresa empresaSalva = service.cadastrarEmpresa(empresa,usuario);
			return new ResponseEntity<Empresa>(empresaSalva, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updatEmpresa ( @RequestBody EmpresaDTO dto) {
		 if(dto == null) 
				return ResponseEntity.badRequest().body("Não foi possivel atualizar a empresa");
		else
			 
		return service.obterPorId(Long.parseLong(dto.getId())).map(
				  entity ->{
					try {
						 
						Empresa empresa = Empresa.builder()
									
								  	.nome(dto.getNome())
									.categoria(dto.getCategoria())
									.cnpj(dto.getCnpj())
									.bio_empresa(dto.getBio_empresa())
									.status_empresa(true)
									.data_atualizacao(LocalDate.now())
								 
									.build();
						  
						  entity = empresa;
						  
						  service.alterarEmpresa(empresa);
						  
						  return ResponseEntity.ok(entity);
					} catch (RegraNegocioException e ) {
						return ResponseEntity.badRequest().body(e.getMessage());
					} 
			 
				}).orElseGet(() 
				-> new ResponseEntity<String>("Empresa não encontrado.",HttpStatus.BAD_REQUEST));
			 
	}
	
	@GetMapping("/buscar")
	public ResponseEntity<?> buscar(
								 @RequestParam(value = "categoria" ,required = false) String categoria,
			 					 @RequestParam(value = "nome",required = false) String nome,
			 					 @RequestParam(value = "cnpj",required = false) String cnpj,
			 					 @RequestParam( "usuario") Long idUsuario
			 					 
				) {
		
		
		Empresa empresaFiltro = new Empresa();
		empresaFiltro.setCategoria(categoria);
		empresaFiltro.setNome(nome);
		empresaFiltro.setCnpj(cnpj);
		  
		 Optional<Usuario> user = userService.obterPorId(idUsuario);
		 
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Usuário não encontrado.");
		 else
			 empresaFiltro.setUsuario(user.get());
		 	
		 List<Empresa> lancamentos = service.buscar(empresaFiltro);
		 
		 
		return ResponseEntity.ok(lancamentos);
		
	}
	
	@GetMapping("/buscar/{id_usuario}/{id_empresa}")
	public ResponseEntity<?> buscar( @PathVariable( "id_usuario") Long idUsuario, @PathVariable( "id_empresa") Long idEmpresa) {
		
		 Empresa empFiltro = new Empresa();
		 
		 Optional<Usuario> user = userService.obterPorId(idUsuario);
		  
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Usuário não encontrado.");
		 else
			 empFiltro.setId(idEmpresa);
		 	 empFiltro.setUsuario(user.get());
		 	
		 Optional<Empresa> empresa = service.buscarPorId(empFiltro);
		 
		 
		return ResponseEntity.ok(empresa.get());
		
	}
	 
}
