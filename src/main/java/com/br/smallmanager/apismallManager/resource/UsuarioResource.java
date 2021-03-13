package com.br.smallmanager.apismallManager.resource;

import java.io.Serializable;
import java.net.URI;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.mail.MessagingException;
import javax.persistence.PostUpdate;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.br.smallmanager.apismallManager.dto.UsuarioEditDTO;
import com.br.smallmanager.apismallManager.dto.UsuarioStartDTO;
import com.br.smallmanager.apismallManager.entity.*;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.UsuarioRepository;
import com.br.smallmanager.apismallManager.service.UsuarioService;
import com.br.smallmanager.apismallManager.utils.EmailSenderService;
 

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
	
	@SuppressWarnings("unused")
	@PutMapping
	public ResponseEntity<?> updateUsuario ( @RequestBody UsuarioEditDTO usuarioEdit) {
		
		return service.obterPorId(usuarioEdit.getId()).map(
				  entity ->{
					try {
				 
						 
						  if(usuarioEdit == null)
							  return ResponseEntity.badRequest().body("Não foi possivel atualizar o status do lançamento.");
						  Usuario usuario = Usuario.builder()
									.nome(usuarioEdit.getNome())
									.email(usuarioEdit.getEmail())
									.sobrenome(usuarioEdit.getSobrenome())
									.sexo(usuarioEdit.getSexo())
									.bio(usuarioEdit.getBio())
									.contato_pessoal(usuarioEdit.getContato_pessoal())
									.email(usuarioEdit.getEmail())
									.senha(usuarioEdit.getSenha())
									.data_atualizacao(new Date())
									.build();
						  
						  service.updateUsuario(entity);
						  
						  return ResponseEntity.ok(entity);
					} catch (RegraNegocioException e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					} 
			 
				}).orElseGet(() 
				-> new ResponseEntity<String>("Usuario não encontrado.",HttpStatus.BAD_REQUEST));
			 
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
	

}