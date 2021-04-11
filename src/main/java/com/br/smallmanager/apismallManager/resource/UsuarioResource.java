package com.br.smallmanager.apismallManager.resource;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

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
 
import com.br.smallmanager.apismallManager.dto.UsuarioEditDTO;
import com.br.smallmanager.apismallManager.dto.UsuarioStartDTO;
import com.br.smallmanager.apismallManager.entity.*;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException; 
import com.br.smallmanager.apismallManager.service.UsuarioService;
import com.br.smallmanager.apismallManager.utils.FotoUploadDisco;
 
  
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioResource implements Serializable{
 
	private static final long serialVersionUID = 1L;
	@Autowired
	private UsuarioService service;
	 
	@GetMapping
	public List<Usuario> listarUsuario(){
		return service.listUsuarios();
	}
	  
	@PostMapping
	public ResponseEntity<?> salvar ( @RequestBody UsuarioStartDTO dto) {
		
		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.data_cadastro(new Date())
				.build();
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity<Usuario>(usuarioSalvo, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	 
	@PutMapping
	public ResponseEntity<?> updateUsuario ( @RequestBody UsuarioEditDTO usuarioEdit) {
		 if(usuarioEdit == null) 
				return ResponseEntity.badRequest().body("Não foi possivel atualizar o usuário.");
		else
			 
		return service.obterPorId(Long.parseLong(usuarioEdit.getId())).map(
				  entity ->{
					try {
						
						SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
						 
						  Date data = formato.parse(usuarioEdit.getData_nascimento());
						  	usuarioEdit.setStatusPerfil(true);
						  Usuario usuario = Usuario.builder()
									
								  	.nome(usuarioEdit.getNome())
									.id(Long.parseLong(usuarioEdit.getId()))
								  	.email(usuarioEdit.getEmail())
									.sobrenome(usuarioEdit.getSobrenome())
									.sexo(usuarioEdit.getSexo())
									.bio(usuarioEdit.getBio())
									.contato_pessoal(usuarioEdit.getContato_pessoal())
									.email(usuarioEdit.getEmail())
									.senha(usuarioEdit.getSenha())
									.data_nascimento(data)
									 
									.codigo_confirmacao(entity.getCodigo_confirmacao())
									
									.build();
						  
						  entity = usuario;
						  
						  service.updateUsuario(usuario);
						  
						  return ResponseEntity.ok(entity);
					} catch (RegraNegocioException | ParseException e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					} 
			 
				}).orElseGet(() 
				-> new ResponseEntity<String>("Usuario não encontrado.",HttpStatus.BAD_REQUEST));
			 
	} 
	@DeleteMapping("/excluirPerfil")
	public ResponseEntity<?>  deleteUsuario ( @RequestBody UsuarioStartDTO dto) {
		 
		  if(dto == null){
				return ResponseEntity.badRequest().body("Não foi possivel atualizar o usúario");
			}
			else{
				return service.obterPorId(dto.getId()).map(
						  entity ->{
							try { 
								  Usuario usuario = Usuario.builder()
											.id(dto.getId())
											.build();  
											 
								  entity = usuario;
								  
								  service.excluirPerfil(usuario);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Usuario não encontrado.",HttpStatus.BAD_REQUEST));
			}  		 
	}
	
	@PostMapping("/uploadFotoPerfil/{id_usuario}")
	public ResponseEntity<?> uploadFotoPerfilUsuario(@PathVariable("id_usuario") Long id_usuario,@RequestParam MultipartFile file){
		 FotoUploadDisco uploadDisco = new FotoUploadDisco();
		 
		 Usuario userUpload = new Usuario();
		 userUpload.setId(id_usuario);
		  //RENOMEAR IMAGEM  
	 	 if(service.obterPorId(userUpload.getId()).isPresent()) {
	 		 userUpload.setImg_login(file.getOriginalFilename());
		 	 uploadDisco.salvarFoto(file);
		 	 service.uploadFotoPerfil(userUpload);
	 	 }else
	 		return  new ResponseEntity<String>("Usuario não encontrado.",HttpStatus.BAD_REQUEST);
	 	
		try {
			return new ResponseEntity<Usuario>(userUpload, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	
	@PostMapping("Ativar/{email}/{codigo}")
	public ResponseEntity<?> atvivarConta (@PathVariable("email") String email,@PathVariable("codigo") String codigo) {
			
		Usuario userAtivado = service.ativarConta(email, codigo);
		
		
		try {
		 
			return new ResponseEntity<Usuario>(userAtivado, HttpStatus.OK);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PostMapping("emailRecuperarSenha/{email}")
	public ResponseEntity<?>  emailRecupersenha (@PathVariable("email") String email ) throws MessagingException {
		Usuario user = new Usuario();
		user.setEmail(email);
	 
		try {
			service.enviarEmailRecupercaoSenha(user);
		} catch (NoSuchAlgorithmException e) {
		  e.printStackTrace();
		} 
		return new ResponseEntity<Usuario>(HttpStatus.CREATED);	 
	}
	
	@PostMapping("RecuperarSenha/{id}/{email}")
	public ResponseEntity<?>  atualizarSenha (@PathVariable("id") String id,@PathVariable("email") String email,@RequestBody UsuarioStartDTO dto){
		Usuario user = new Usuario();
		
		user.setEmail(email);
		user = service.findByEmail(user.getEmail());
		String encript = service.encriptar(user.getId().toString());
		user.setSenha(dto.getSenha());
		if(encript.equals(id)) {

		 service.recuperarSenha(user);
			 	
		}else {
			return  new ResponseEntity<String>("Usuario não encontrado.",HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<Usuario>(HttpStatus.OK);	 
	}

}
