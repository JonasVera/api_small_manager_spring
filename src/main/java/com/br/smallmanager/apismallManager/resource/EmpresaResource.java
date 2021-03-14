package com.br.smallmanager.apismallManager.resource;

import java.time.LocalDate;
  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.EmpresaDTO;
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.service.EmpresaService;

@RestController
@RequestMapping("api/empresa")
public class EmpresaResource {

	@Autowired
	private EmpresaService service;
	 
	@PostMapping
	public ResponseEntity<?> cadastrarEmpresa ( @RequestBody EmpresaDTO dto) {
		
		Usuario usuario = new Usuario ();
		usuario.setId(Long.parseLong(dto.getUsuario()));
		
		Empresa empresa = Empresa.builder()
				.nome(dto.getNome())
				.categoria(dto.getCategoria())
				.cnpj(dto.getCnpj())
				.bio_empresa(dto.getBio_empresa())
				.status_empresa(true)
				.data_atualizacao(LocalDate.now())
				.data_cadastro(LocalDate.now())
				.build();
		try {
			Empresa empresaSalva = service.cadastrarEmpresa(empresa,usuario);
			return new ResponseEntity<Empresa>(empresaSalva, HttpStatus.CREATED);
		}catch (RegraNegocioException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
	 
}
