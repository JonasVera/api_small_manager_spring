package com.br.smallmanager.apismallManager.resource;
  
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.br.smallmanager.apismallManager.dto.MensagemDto;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.Mensagem;
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.MensagemService;
import com.br.smallmanager.apismallManager.service.UsuarioService;
 
@RestController
@RequestMapping("api/empresa/mensagem")
@CrossOrigin(origins = "*")
public class MensagemResource {

	@Autowired
	private MensagemService service;
	@Autowired
	private UsuarioService serviceUser;
	
	@PostMapping
	public ResponseEntity<?> cadastrarMensagem ( @RequestBody MensagemDto dto) {
		 Empresa emp = new Empresa();
		 emp.setId(dto.getEmpresa());

		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		 Date hora = Calendar.getInstance().getTime(); 
		 String dataFormatada = sdf.format(hora);
		 
		Mensagem mensagem = Mensagem.builder()
				.nome(dto.getNome())
				.lido(false)
				.assunto(dto.getAssunto())
				.hora(dataFormatada)
				.mensagem(dto.getMensagem()) 
				.tipo(dto.getTipo())
				.email(dto.getEmail())
				.empresa(emp)
				.dataEnvio(new Date())
				.build();
		try {
			Mensagem msg = service.cadastrarMensagem(mensagem);
			try {
				serviceUser.enviarMensagem(dto);
			} catch (NoSuchAlgorithmException e) { 
				e.printStackTrace();
			}
			return new ResponseEntity<Mensagem>(msg, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@PostMapping("email")
	public ResponseEntity<?> enviaEmail ( @RequestBody MensagemDto dto) { 
		try { 
			try {
				serviceUser.enviarMensagem(dto);
			} catch (NoSuchAlgorithmException e) { 
				e.printStackTrace();
			}
			return new ResponseEntity<MensagemDto>(dto, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	@GetMapping("listar/{id_empresa}")
	public List<Mensagem> mensagens(@PathVariable( "id_empresa") Long id_empresa){
		Empresa emp = new Empresa();
		emp.setId(id_empresa);
		
		return service.mensagemPorEmpresa(emp);
	}
	
	@GetMapping("conversa/{email}/{tipo}")
	public List<Mensagem> mensagens(@PathVariable( "email") String email, @PathVariable( "tipo") String tipo){
		 Mensagem msg = new Mensagem();
		 msg.setEmail(email);
		 msg.setTipo(tipo); 
		return service.conversaTipo(msg);
	}
	 
	@DeleteMapping("/excluirMensagem/{id_mensagem}")
	public ResponseEntity<?>  deleteCategoria ( @PathVariable( "id_categoria") Long id_mensagem) {
		 
		  if(id_mensagem == null){
				return ResponseEntity.badRequest().body("Não foi possivel localizar a mensagem");
			}
			else{
				return service.obterPorId(id_mensagem).map(
						  entity ->{
							try { 
								  Mensagem msg = Mensagem.builder()
											.id(id_mensagem)
											.build();   
								  entity = msg; 
								  service.excluirMensagem(msg);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Categoria não encontrada.",HttpStatus.BAD_REQUEST));
			}  		 
	} 
	  
}
