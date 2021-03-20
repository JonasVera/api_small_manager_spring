package com.br.smallmanager.apismallManager.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
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
	
	@Transactional 
	public Empresa cadastrarEmpresa(Empresa empresa,Usuario usuario) {
		
		Optional<Usuario> user = userService.obterPorId(usuario.getId());
		
		if (!user.isEmpty()) {
			empresa.setUsuario(usuario);
				return repository.save(empresa);
		}else {
			throw new RegraNegocioException("Não foi possivel cadastrar empresa, usuario não encontrado.");
		}
		 
	}
	
	 
	@Transactional 
	public List<Empresa> buscar(Empresa empresaFiltro) {
		Example<Empresa> example = Example.of(empresaFiltro, ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		 
		return repository.findAll(example);
	}
	
	@Transactional 
	public Optional<Empresa> buscarPorId(Empresa empresa) {
	 
		 if	(empresa.getUsuario().getId() != null) {
			 return repository.findById(empresa.getId());
		 }else {
			 throw new RegraNegocioException("Empresa não encontrada para este usúario.");
		 } 
	}
 
	public void alterarEmpresa(Empresa empresa) {
		repository.save(empresa);
	}
	@Transactional 
	public void listarEmrpesa() {
		repository.findAll();
	}
	@Transactional 
	public Optional<Empresa> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	public void excluirEmpresa(Empresa empresa) {
	  repository.delete(empresa);
	}
	
	public void validaEmpresa(Empresa empresa) {
		 
		if (repository.findById(empresa.getId()).isPresent() == false) {
			throw new RegraNegocioException("Empresa não existe !");
		}
	}
}
