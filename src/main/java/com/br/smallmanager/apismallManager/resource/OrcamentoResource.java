package com.br.smallmanager.apismallManager.resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
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
import com.br.smallmanager.apismallManager.dto.ContatoDTO;
import com.br.smallmanager.apismallManager.dto.OrcamentoDTO;
import com.br.smallmanager.apismallManager.entity.Contato;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.EventoAgenda;
import com.br.smallmanager.apismallManager.entity.Orcamento;
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.ContatoService;
import com.br.smallmanager.apismallManager.service.EmpresaService;
import com.br.smallmanager.apismallManager.service.EventoAgendaService;
import com.br.smallmanager.apismallManager.service.OrcamentoService;
 

@RestController
@RequestMapping("api/empresa/orcamento")
@CrossOrigin(origins = "*")
public class OrcamentoResource {

	@Autowired
	private OrcamentoService service;  
	
	@PostMapping
	public ResponseEntity<?> salvarOrcamento( @RequestBody OrcamentoDTO dto) {
		
		Orcamento eventoAgenda = Orcamento.builder()
				 .titulo(dto.getTitulo())
				 .descricao(dto.getDescricao())
				 .status("PENDENTE")
				 .data_solicitacao(new Date())
				 .Contatocliente(dto.getEmail()) 
				 .build();
		 
			
		Produto produto = new Produto();
		produto.setId(dto.getProduto());
		if (produto.getId() == null)
				return ResponseEntity.badRequest().body("Não foi possivel cadastrar o evento para este produto.");
		else
			 
			eventoAgenda.setProduto(produto);
				  
				try { 
					Orcamento evento = service.cadastrarOrcamento(eventoAgenda);
						return new ResponseEntity<Orcamento>(evento, HttpStatus.CREATED);
				}catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}			 
	}
	
	@DeleteMapping("/{id_orcamento}")
	public ResponseEntity<?>  deleteOrcamento(@PathVariable( "id_evento") Long id_orcamento) {
		
		Orcamento orcamento = Orcamento.builder()
				.id(id_orcamento)
				.build(); 
		 service.excluirEvento(orcamento);	
						 
		 return new ResponseEntity<>(HttpStatus.OK);
				 		 
	} 
	
	@GetMapping("/{produto}")
	public ResponseEntity<?> buscar( @PathVariable( "produto") Long produto) {
		   Produto prod = new Produto();
		  prod.setId(produto);
		 return ResponseEntity.ok(service.buscarOrcamentoProduto(prod));
		
	}
	
}
