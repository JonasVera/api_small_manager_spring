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
import com.br.smallmanager.apismallManager.dto.EnderecoEmpresaDTO;
import com.br.smallmanager.apismallManager.entity.Contato;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.EnderecoEmpresa;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.ContatoService;
import com.br.smallmanager.apismallManager.service.EmpresaService;
import com.br.smallmanager.apismallManager.service.EnderecoEmpresaService;
 

@RestController
@RequestMapping("api/empresa/endereco")
@CrossOrigin(origins = "*")
public class EnderecoEmpresaResource {

 
	@Autowired
	private EnderecoEmpresaService service;
	
	@Autowired
	private EmpresaService empresaservice;
	
	@PostMapping
	public ResponseEntity<?> cadastrarContato ( @RequestBody EnderecoEmpresaDTO dto) {
		Empresa empresa = new Empresa();
		empresa.setId(dto.getEmpresa());
		
		EnderecoEmpresa enderecoEmpresa = EnderecoEmpresa.builder()
				.cidade(dto.getCidade()) 
				.uf(dto.getUf())
				.cep(dto.getCep())
				.logradouro(dto.getLogradouro())
				.numero(dto.getNumero()) 
				.build();
		  
		if (empresa.getId() == null)
				return ResponseEntity.badRequest().body("Não foi possivel cadastrar o endereço para essa empresa.");
		else
			 
			enderecoEmpresa.setEmpresa(empresa);
				  
				try { 
					EnderecoEmpresa EnderecoEmpresaSalvo = service.cadastrarEnderecoEmpresa(enderecoEmpresa);
						return new ResponseEntity<EnderecoEmpresa>(EnderecoEmpresaSalvo, HttpStatus.CREATED);
				}catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}			 
	}
	 
	@PostMapping("/update")
	public ResponseEntity<?> updateContato ( @RequestBody EnderecoEmpresaDTO dto) {
		Empresa empresa = new Empresa();
		empresa.setId(dto.getEmpresa());
		EnderecoEmpresa enderecoEmpresa = EnderecoEmpresa.builder()
				.cidade(dto.getCidade()) 
				.uf(dto.getUf())
				.cep(dto.getCep())
				.logradouro(dto.getLogradouro())
				.numero(dto.getNumero()) 
				.build();
		   
		if (empresa.getId() == null)
			return ResponseEntity.badRequest().body("Não foi possivel cadastrar o endereço para essa empresa.");
		else
			 
			enderecoEmpresa.setEmpresa(empresa);
				  
				try {  
						EnderecoEmpresa EnderecoEmpresaSalvo = service.cadastrarEnderecoEmpresa(enderecoEmpresa);
						return new ResponseEntity<EnderecoEmpresa>(EnderecoEmpresaSalvo, HttpStatus.CREATED);
				}catch (RegraNegocioException e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}	
 
	}
	  
}
