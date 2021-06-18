package com.br.smallmanager.apismallManager.resource;
 
import java.time.LocalDate;
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
import org.springframework.web.multipart.MultipartFile;

import com.br.smallmanager.apismallManager.dto.EmpresaDTO; 
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.EmpresaService;
import com.br.smallmanager.apismallManager.service.UsuarioService;
import com.br.smallmanager.apismallManager.utils.FotoUploadDisco;

@RestController
@RequestMapping("api/empresa")
@CrossOrigin(origins = "*")
public class EmpresaResource {

	@Autowired
	private EmpresaService service;
	@Autowired
	private UsuarioService userService;
	@Autowired
	 private com.br.smallmanager.apismallManager.service.S3StorageService serviceS3;
	@PostMapping
	public ResponseEntity<?> cadastrarEmpresa ( @RequestBody EmpresaDTO dto) {
		
		Usuario usuario = new Usuario ();
		usuario.setId(dto.getUsuario());
		System.out.println(usuario.getId());
		
		Empresa empresa = Empresa.builder()
				.nome(dto.getNome())
				.categoria(dto.getCategoria())
				.cnpj(dto.getCnpj())
				.bio_empresa(dto.getBio_empresa())
				.status_empresa(true)
				.data_atualizacao(LocalDate.now())
				.bannerColor(dto.getBannerColor())
				.data_cadastro(LocalDate.now())
				.slung(dto.getNome().toLowerCase().trim().replace(" ","_"))
				.orcamento_cli(dto.getOrcamento_cli())
				.agendamento_cli(dto.getAgendamento_cli())
				.build();
		try {
			Empresa empresaSalva = service.cadastrarEmpresa(empresa,usuario);
			return new ResponseEntity<Empresa>(empresaSalva, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseEntity<?> updateEmpresa ( @RequestBody EmpresaDTO dto) {
		 if(dto == null) 
				return ResponseEntity.badRequest().body("Não foi possivel atualizar a empresa");
		else {
			
			return service.obterPorId(dto.getId()).map(
					  entity ->{
						try {
							 Usuario usuario = new Usuario ();
							 usuario.setId(dto.getUsuario());
							  
							 if (userService.obterPorId(usuario.getId()).isPresent()) {
									Empresa empresa = Empresa.builder()
											.id(dto.getId())
										  	.nome(dto.getNome())
											.categoria(dto.getCategoria())
											.cnpj(dto.getCnpj())
											.bio_empresa(dto.getBio_empresa())
											.status_empresa(true)
											.bannerColor(dto.getBannerColor())
											.slung(dto.getNome().toLowerCase().trim().replace(" ","_"))
											.data_cadastro(entity.getData_cadastro())
											.data_atualizacao(LocalDate.now())
											.orcamento_cli(dto.getOrcamento_cli())
											.agendamento_cli(dto.getAgendamento_cli())
											.usuario(usuario)
											.build();
								  
								  entity = empresa;
								  
								  service.alterarEmpresa(empresa);
								  return   ResponseEntity.ok(entity);
									 
							 }else {
								 return new ResponseEntity<String>("Usuário não entrado.",HttpStatus.BAD_REQUEST);
							 } 
							 
						} catch (RegraNegocioException e ) {
							return ResponseEntity.badRequest().body(e.getMessage());
						} 
				 
					}).orElseGet(() 
					-> new ResponseEntity<String>("Empresa não encontrado.",HttpStatus.BAD_REQUEST));
		}
			  
	}
	
	
	@DeleteMapping("/excluirEmpresa")
	public ResponseEntity<?>  deleteEmpresa ( @RequestBody EmpresaDTO dto) {
		 
		  if(dto == null){
				return ResponseEntity.badRequest().body("Não foi possivel localizar empresa");
			}
			else{
				return service.obterPorId(dto.getId()).map(
						  entity ->{
							try { 
								  Empresa empresa = Empresa.builder()
											.id(dto.getId())
											.build();  
											 
								  entity = empresa;
								  
								  service.excluirEmpresa(empresa);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Usuario não encontrado.",HttpStatus.BAD_REQUEST));
			}  		 
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
 
	@GetMapping("/buscar/{id_usuario}")
	public ResponseEntity<?> buscar( @PathVariable( "id_usuario") Long idUsuario) {
		 
	   System.out.println(idUsuario);
		 Optional<Usuario> user = userService.obterPorId(idUsuario);
		  
		 if(!user.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. Usuário não encontrado.");
	  
		return ResponseEntity.ok(service.buscarPorUsuario(user.get()));
		
	}
	@GetMapping("/nome/{empresa}")
	public ResponseEntity<?> buscar( @PathVariable( "empresa") String empresa) {
		   
		return ResponseEntity.ok(service.buscarPorNome(empresa));
		
	}
	
	@PostMapping("/uploadLogoTipo/{id_empresa}")
	public ResponseEntity<?> uploadFotoPerfilEmpresa(@PathVariable("id_empresa") Long id_empresa,@RequestParam MultipartFile file){
		 FotoUploadDisco uploadDisco = new FotoUploadDisco();
		 
		 Empresa userUpload = new Empresa();
		 userUpload.setId(id_empresa);
		  //RENOMEAR IMAGEM  
	 	 if(service.obterPorId(userUpload.getId()).isPresent()) { 
	 		userUpload.setImg_logotipo(serviceS3.uploadFile(file));
		 	service.uploadFotoLogo(userUpload);
	 	 }else
	 		return  new ResponseEntity<String>("Empresa não encontrada.",HttpStatus.BAD_REQUEST);
	 	
		try {
			return new ResponseEntity<Empresa>(userUpload, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	 
}
