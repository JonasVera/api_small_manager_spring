package com.br.smallmanager.apismallManager.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 
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
import com.br.smallmanager.apismallManager.dto.AgendamentoDTO; 
import com.br.smallmanager.apismallManager.entity.EventoAgenda;
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException; 
import com.br.smallmanager.apismallManager.service.EmpresaService;
import com.br.smallmanager.apismallManager.service.EventoAgendaService;
 

@RestController
@RequestMapping("api/empresa/agenda")
@CrossOrigin(origins = "*")
public class EventoAgendaResource {

 
	@Autowired
	private EventoAgendaService service; 
	
	@Autowired
	private EmpresaService Empresaservice; 
	
	@PostMapping
	public ResponseEntity<?> cadastrarEvento( @RequestBody AgendamentoDTO dto) {
		
		EventoAgenda eventoAgenda = EventoAgenda.builder()
				.titulo(dto.getTitulo())
				.descricao(dto.getDescricao())
				.status("PENDENTE")
				 .hora(dto.getHoraInicio())
				 .Contatocliente(dto.getEmail())
				.build();
		
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		 Date data = new Date();
			if(dto.getDataInicio() != null) {
				   try {
					data = formato.parse(dto.getDataInicio());
				} catch (ParseException e) {
					 
					e.printStackTrace();
				}
			}
			eventoAgenda.setData_inicio(data);
			
		Produto produto = new Produto();
		produto.setId(dto.getProduto());
		if (produto.getId() == null)
				return ResponseEntity.badRequest().body("Não foi possivel cadastrar o evento para este produto.");
		else
			 
			eventoAgenda.setProduto(produto);
				  
				try { 
						EventoAgenda evento = service.cadastrarEvento(eventoAgenda);
						return new ResponseEntity<EventoAgenda>(evento, HttpStatus.CREATED);
				}catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}			 
	}
	
	@DeleteMapping("/{id_evento}")
	public ResponseEntity<?>  deleteEvento(@PathVariable( "id_evento") Long id_evento) {
		
		EventoAgenda evento = EventoAgenda.builder()
				.id(id_evento)
				.build(); 
		 service.excluirEvento(evento);	
						 
		 return new ResponseEntity<>(HttpStatus.OK);
				 		 
	} 
	
	@PostMapping("status/{id_evento}/{status}")
	public ResponseEntity<?>  alterarStatus(@PathVariable( "id_evento") Long id_evento,
			@PathVariable( "id_evento") String status) {
		
		EventoAgenda evento = EventoAgenda.builder()
				.id(id_evento) 
				.build(); 
		evento = service.buscarPorId(evento).get();
		evento.setStatus(status);
		
		 service.alterarEmpresa(evento);
						 
		 return new ResponseEntity<>(HttpStatus.OK);
				 		 
	} 
	
	@GetMapping("/agenda/{produto}")
	public ResponseEntity<?> buscar( @PathVariable( "produto") Long produto) {
		   Produto prod = new Produto();
		  prod.setId(produto);
		 return ResponseEntity.ok(service.buscarAgendaProduto(prod));
		
	}
	
}
