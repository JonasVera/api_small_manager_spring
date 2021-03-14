package com.br.smallmanager.apismallManager.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.EmpresaRepository;

@Service
public class EmpresaService {

	@Autowired
	private EmpresaRepository repository;
	
	@Autowired 
	private UsuarioService userService;
	
	
	public Empresa cadastrarEmpresa(Empresa empresa,Usuario usuario) {
		
		Optional<Usuario> user = userService.obterPorId(usuario.getId());
		
		if (!user.isEmpty()) {
			empresa.setUsuario(usuario);
				return repository.save(empresa);
		}else {
			throw new RegraNegocioException("Não foi possivel cadastrar empresa, usuario não encontrado.");
		}
		
		
	}
	
	public void alterarEmpresa(Empresa empresa) {
		repository.save(empresa);
	}
	
	public void listarEmrpesa() {
		repository.findAll();
	}
	
	public void excluirEmpresa(Empresa empresa) {
	  repository.delete(empresa);
	}
	
}
