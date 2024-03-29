package com.br.smallmanager.apismallManager.resource;

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
import com.br.smallmanager.apismallManager.dto.ContatoDTO;
import com.br.smallmanager.apismallManager.entity.Contato;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.ContatoService;
import com.br.smallmanager.apismallManager.service.EmpresaService;
 

@RestController
@RequestMapping("api/empresa/contato")
@CrossOrigin(origins = "*")
public class ContatoResource {

 
	@Autowired
	private ContatoService service;
	
	@Autowired
	private EmpresaService empresaservice;
	
	@PostMapping
	public ResponseEntity<?> cadastrarContato ( @RequestBody ContatoDTO dto) {
		
		Contato contato = Contato.builder()
				.nome(dto.getNome())
				.categoria(dto.getCategoria())
				.contato(dto.getContato())
				.build();
		
		Empresa empresa = new Empresa();
		empresa.setId(Long.parseLong(dto.getEmpresa()));
		if (empresa.getId() == null)
				return ResponseEntity.badRequest().body("Não foi possivel cadastrar o contato na empresa.");
		else
			 
				contato.setEmpresa(empresa);
				  
				try { 
						Contato contatoSalvo = service.cadastrarContato(contato);
						return new ResponseEntity<Contato>(contatoSalvo, HttpStatus.CREATED);
				}catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}			 
	}
	
	@DeleteMapping
	public ResponseEntity<?>  deleteContato ( @RequestBody ContatoDTO dto) {
		
		Contato contato = Contato.builder()
				.id(Long.parseLong(dto.getId()))
				.build();
		
		Empresa empresa = new Empresa();
		empresa.setId(Long.parseLong(dto.getEmpresa()));
		if (empresa.getId() == null)
				return ResponseEntity.badRequest().body("Não foi possivel cadastrar o contato na empresa.");
		else
				contato.setEmpresa(empresa);
				 service.excluirContato(contato);
						 
				  return new ResponseEntity<>(HttpStatus.OK);
				 		 
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> updateContato ( @RequestBody ContatoDTO dto) {

		Contato contato = Contato.builder()
				.id(Long.parseLong(dto.getId()))
				.nome(dto.getNome())
				.categoria(dto.getCategoria())
				.contato(dto.getContato())			
				.build();
		
		Empresa empresa = new Empresa();
		 
		 
		empresa.setId(Long.parseLong(dto.getEmpresa()));
		
		if (empresa.getId() == null)
				return ResponseEntity.badRequest().body("Não foi possivel cadastrar o contato na empresa.");
		else
			 
				contato.setEmpresa(empresa);
				  
				try { 
						Contato contatoSalvo = service.cadastrarContato(contato);
						return new ResponseEntity<Contato>(contatoSalvo, HttpStatus.CREATED);
				}catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}	
 
	}
	
	@GetMapping("/buscar/{id_contato}/{id_empresa}")
	public ResponseEntity<?> buscar( @PathVariable( "id_contato") Long idContato, @PathVariable( "id_empresa") Long idEmpresa) {
		
		 Contato contatofiltro = new Contato();
		 
		 Optional<Empresa> empresa = empresaservice.obterPorId(idEmpresa);
		  
		 if(!empresa.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. empresa não encontrada.");
		 else
			 contatofiltro.setId(idContato);
		 	 contatofiltro.setEmpresa(empresa.get());
		 	
		 Optional<Contato> contatoList = service.buscarPorId(contatofiltro);
		  
		return ResponseEntity.ok(contatoList.get());
		
	}
	
	@GetMapping("/buscar/{empresa}")
	public ResponseEntity<?> buscar( @PathVariable( "empresa") Long idEmpresa) {
		 
		 Optional<Empresa> empresa = empresaservice.obterPorId(idEmpresa);
		  
		 if(!empresa.isPresent())
			 return ResponseEntity.badRequest().body("Não foi possivel realizar a consulta. empresa não encontrada.");
		 else
			  
		return ResponseEntity.ok(service.buscarContatoEmpresa(empresa.get()));
		
	}
}
