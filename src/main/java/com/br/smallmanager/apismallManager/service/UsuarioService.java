package com.br.smallmanager.apismallManager.service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.UsuarioRepository;
import com.br.smallmanager.apismallManager.utils.Email;
import com.br.smallmanager.apismallManager.utils.EmailSenderService;
import com.br.smallmanager.apismallManager.utils.Mailer;
import com.br.smallmanager.apismallManager.utils.Mensagem;

import ch.qos.logback.classic.Logger;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	 	@Autowired
	    EmailSenderService emailService;
	 
	 	@Autowired
		private EmailSenderService serviceMail;
	
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		usuario.setCodigo_confirmacao(codigoEmail());
		try {
			triggerMail(usuario);
		} catch (MessagingException e) {
			 
			e.printStackTrace();
		} 
		return	repository.save(usuario);
	  
	}
	
	public Usuario updateUsuario(Usuario usuario) {
		
		Objects.requireNonNull(usuario.getId());
	 
		return	repository.save(usuario);
	  
	}
	
	
	public List<Usuario> listUsuarios (){
		return repository.findAll();
	}
	 
	private String codigoEmail() {
		 Random gerador = new Random();
		 String codigo = "";
		 int codGerado; 
		for (int i = 0; i <5; i++) {
			codGerado = gerador.nextInt(100);
		  	codigo += codigo = (Integer.toString(codGerado));
		} 
		return codigo.substring(5);
	}
	
	public Optional<Usuario> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	public boolean validarEmail(String email) {
		boolean existe =  repository.existsByEmail(email);
		
		if (existe)
			throw new RegraNegocioException("Já existe um usuário cadastrado com este email.");
		return existe;
				
	}
	
	public Usuario ativarConta(String email, String codigoConfirmacao) {
		
		 Usuario user = repository.findByEmail(email);
		 
		 if (user.getEmail() != null || user.getEmail() != "") {
			 if(user.getCodigo_confirmacao().equals(codigoConfirmacao)) {
				 user.setStatus_perfil(true);
				 repository.save(user);
				 
			 }else
				 throw new RegraNegocioException("Não foi possivel ativar a conta com este código de ativação.");
				 
		 }
		 return user;
	}
	
	private void triggerMail(Usuario usuario) throws MessagingException {

		serviceMail.sendSimpleEmail(usuario.getEmail(),
				"Informe o codigo a seguir para ativar sua conta: "+usuario.getCodigo_confirmacao()+" ",
				"Código de ativação da sua conta" );
	}
}
