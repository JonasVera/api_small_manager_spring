package com.br.smallmanager.apismallManager.service;
 
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
 
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.dto.MensagemDto;
import com.br.smallmanager.apismallManager.entity.Mensagem;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.UsuarioRepository;
import com.br.smallmanager.apismallManager.utils.EmailSenderService;

 
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;

	 	@Autowired
	    EmailSenderService emailService;
	 
	 	@Autowired
		private EmailSenderService serviceMail;

	 	@Value("${url.front}")
	 	private String url_front;
	 	 
	
	public Usuario salvarUsuario(Usuario usuario) {
		validarEmail(usuario.getEmail());
		//usuario.setCodigo_confirmacao(codigoEmail());
		//try {
			//triggerMail(usuario);
		//} catch (MessagingException e) {
			 
		//	e.printStackTrace();
		//} 
		BCryptPasswordEncoder encoderPassword = new BCryptPasswordEncoder();
		usuario.setSenha(encoderPassword.encode(usuario.getSenha())); 
		return	repository.save(usuario);
	  
	}
	
	public void uploadFotoPerfil(Usuario user){
	 	Usuario userUp = new Usuario(); 
	
		if (obterPorId(user.getId()).isPresent()) {
			
			if (userUp.getImg_login() != null || userUp.getImg_login() != "") {
				userUp = obterPorId(user.getId()).get();
				userUp.setImg_login(user.getImg_login()); 
			}
			repository.save(userUp);
		}else
			throw new RegraNegocioException("Usuário não encontrado."); 
	}
	
	public Usuario updateUsuario(Usuario usuario) {
		
		Objects.requireNonNull(usuario.getId());
				usuario.setStatus_perfil(true);
				BCryptPasswordEncoder encoderPassword = new BCryptPasswordEncoder();
				usuario.setSenha(encoderPassword.encode(usuario.getSenha())); 
		return	repository.save(usuario);
	  
	} 
	public List<Usuario> listUsuarios (){
		return repository.findAll();
	}
	public void uploadFotoPerfilStorage(Usuario user){
		Objects.requireNonNull(user.getId());
		
	 	Usuario userUp = new Usuario(); 
		userUp = obterPorId(user.getId()).get();
		userUp.setImg_login(user.getImg_login()); 
		 
		repository.save(userUp);
	}
	private String codigoEmail() {
		 Random gerador = new Random();
		 String codigo = "";
		 int codGerado; 
		for (int i = 0; i <5; i++) {
			codGerado = gerador.nextInt(1000);
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
	public void excluirPerfil(Usuario usuario) {
		Objects.requireNonNull(usuario.getId());
		repository.delete(usuario);
		
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
	
	@Transactional
	public void recuperarSenha(Usuario usuario) {
		
		
		Objects.requireNonNull(usuario.getId());
		if (usuario.getId() == null) {
			 	throw new RegraNegocioException("Identificador do usuário está inválido !");
		} 
		
		Usuario usuarioCadastrado = repository.findByEmail(usuario.getEmail());
		
		usuarioCadastrado.setSenha(usuario.getSenha());
		
		BCryptPasswordEncoder encoderPassword = new BCryptPasswordEncoder();
		
		usuarioCadastrado.setSenha(encoderPassword.encode(usuario.getSenha()));
	 
		 System.out.println("\n SENHA 2 "+usuarioCadastrado.getSenha());
		
		repository.save(usuarioCadastrado);		
	}
	 
	
	public void enviarEmailRecupercaoSenha(Usuario user) throws NoSuchAlgorithmException {
		Usuario usuarioEmail = repository.findByEmail(user.getEmail());
		
		if (usuarioEmail == null) {
			 throw new RegraNegocioException("E-mail inválido !");
		} 
		  
		String textoId = encriptar(usuarioEmail.getId().toString()); 
		try {
			
			serviceMail.sendEmailWithAttachment(user.getEmail(),
					url_front+"?id="+textoId+"&email="+usuarioEmail.getEmail(),
				 "Recuperação de senha" );
		} catch (MessagingException e) {
			 e.printStackTrace();
		}

	}
	
	public  String encriptar (String texto) {
		 
		try {
			Date data = new Date();
			SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy"); 
  
			formatador.format( data );
			
			texto = texto + formatador.format(data).toString();
			
			MessageDigest md = null;
			
			md = MessageDigest.getInstance("MD5");
			
			BigInteger hash = new BigInteger(1,md.digest(texto.getBytes()));
			
			texto =  hash.toString(16);
			
		} catch (NoSuchAlgorithmException e) {
			 e.printStackTrace();
		}
	
		return texto;
	}
	
	 
	public void enviarMensagem(MensagemDto mensagem) throws NoSuchAlgorithmException {

		try {
			serviceMail.mensagemEmail(mensagem.getEmail(),
					mensagem.getMensagem(),
					mensagem.getAssunto(),
					mensagem);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	public Usuario findByEmail(String email) {
		 
		return repository.findByEmail(email);
				
	}
}
