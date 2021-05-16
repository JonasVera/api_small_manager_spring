package com.br.smallmanager.apismallManager.resource;
  
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date; 
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
import org.springframework.web.bind.annotation.RestController; 
import com.br.smallmanager.apismallManager.constants.Status;
import com.br.smallmanager.apismallManager.dto.AgendamentoDTO;
import com.br.smallmanager.apismallManager.entity.Agendamento;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.AgendamentoService;
 
@RestController
@RequestMapping("api/agendamento")
public class AgendamentoResource {

	@Autowired
	private AgendamentoService service;
	
	 
	@PostMapping
	public ResponseEntity<?> cadastrarAgendamento ( @RequestBody AgendamentoDTO dto) throws ParseException {
		 
		Empresa emp = new Empresa();
		emp.setId(dto.getId());
		
		String dataRecebida = dto.getDataInicio(); 
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
		Date dataFormatada = formato.parse(dataRecebida); 
		Agendamento Agendamento = com.br.smallmanager.apismallManager.entity.Agendamento.builder()
				.titulo(dto.getTitulo())
				.empresa(emp)
				.descricao(dto.getDescricao())
				.email_cliente(dto.getEmail())
				.data_inicio(dataFormatada)
				.hora_inicio(dto.getHoraInicio())
				.status(Status.ATIVO.toString())
				.build();
		try {
			Agendamento AgendamentoSalva = service.cadastrarAgendamento(Agendamento);
			return new ResponseEntity<Agendamento>(AgendamentoSalva, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	
	@PutMapping("/alterarStatus/{id_agenda}/{status}")
	public ResponseEntity<?>  atualizarStatus ( @PathVariable( "id_agenda") Long id_agenda,@PathVariable( "status") String status) {
		 
		if(id_agenda == null){
            return ResponseEntity.badRequest().body("Não foi possivel localizar o evento ");
        }
			else{
				return service.obterPorId(id_agenda).map(
						  entity ->{
							try {   
									  service.atualizarStatus(status,id_agenda); 
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Produto não encontrado.",HttpStatus.BAD_REQUEST));
			}  		 
	}
	
	@PutMapping
	public ResponseEntity<?> updateAgendamento (@RequestBody AgendamentoDTO dto) {
		 if(dto == null || dto.getId() == null || dto.getId().toString() == " " ) 
				return ResponseEntity.badRequest().body("Não foi possivel atualizar o Agendamento");
		else {
		
			return service.obterPorId(dto.getId()).map(
					  entity ->{
						try {
							 Agendamento Agendamento = new Agendamento ();
							 Agendamento.setId(dto.getId());
							  
				 
								 Empresa emp = new Empresa();
									emp.setId(dto.getId());
									
									String dataRecebida = dto.getDataInicio(); 
									SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy"); 
									Date dataFormatada = null;
									try {
										dataFormatada = formato.parse(dataRecebida);
									} catch (ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} 
									Agendamento AgendamentoEdit = com.br.smallmanager.apismallManager.entity.Agendamento.builder()
											.titulo(dto.getTitulo())
											.empresa(emp)
											.descricao(dto.getDescricao())
											.email_cliente(dto.getEmail())
											.data_inicio(dataFormatada)
											.hora_inicio(dto.getHoraInicio())
											.status(Status.ATIVO.toString())
											.build();
											 
								  entity = AgendamentoEdit; 
								  service.alterarAgendamento(AgendamentoEdit); 
							  return ResponseEntity.ok(entity);
						} catch (RegraNegocioException e ) {
							return ResponseEntity.badRequest().body(e.getMessage());
						} 
				 
					}).orElseGet(() 
					-> new ResponseEntity<String>("Agendamento não encontrada.",HttpStatus.BAD_REQUEST));
		}   
	}
	 
	@DeleteMapping("/excluirAgendamento/{id_Agendamento}")
	public ResponseEntity<?>  deleteAgendamento ( @PathVariable( "id_Agendamento") Long idAgendamento) {
		 
		  if(idAgendamento == null){
				return ResponseEntity.badRequest().body("Não foi possivel localizar Agendamento");
			}
			else{
				return service.obterPorId(idAgendamento).map(
						  entity ->{
							try { 
								  Agendamento Agendamento = com.br.smallmanager.apismallManager.entity.Agendamento.builder()
											.id(idAgendamento)
											.build();   
								  entity = Agendamento; 
								  service.excluirAgendamento(Agendamento);
								  
								  return new ResponseEntity<>(HttpStatus.OK);
							} catch (RegraNegocioException e) {
								return ResponseEntity.badRequest().body(e.getMessage());
							} 
					 
						}).orElseGet(() 
						-> new ResponseEntity<String>("Agendamento não encontrada.",HttpStatus.BAD_REQUEST));
			}  		 
	} 
	 
	@GetMapping("/buscar/{id_Agendamento}")
	public ResponseEntity<?> buscar( @PathVariable( "id_agendamento") Long idAgendamento) {
		
		 Agendamento empFiltro = new Agendamento();
		   
	 
			 empFiltro.setId(idAgendamento);
		 	  
		 Optional<Agendamento> Agendamento = service.buscarPorId(empFiltro);
		  
		return ResponseEntity.ok(Agendamento.get());
		
	}


}
