package com.br.smallmanager.apismallManager.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.smallmanager.apismallManager.entity.EnderecoEmpresa;
import com.br.smallmanager.apismallManager.entity.Empresa; 
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.EnderecoEmpresaRepository;

@Service
public class EnderecoEmpresaService {
	
	@Autowired
	private EnderecoEmpresaRepository repository;
	@Autowired
	private EmpresaService empresaService;

	@Transactional 
	public EnderecoEmpresa cadastrarEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
		 return repository.save(enderecoEmpresa);
			 
	}
	
	@Transactional 
	public Optional<EnderecoEmpresa> buscarPorId(EnderecoEmpresa enderecoEmpresa) {
	 
		 if	(enderecoEmpresa.getEmpresa().getId() != null) {
			 return repository.findById(enderecoEmpresa.getId());
		 }else {
			 throw new RegraNegocioException("EnderecoEmpresa não encontrado para esta empresa.");
		 } 
	}
	 
	@Transactional 
	public Optional<EnderecoEmpresa> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	@Transactional 
	public List<EnderecoEmpresa> buscarEnderecoEmpresaEmpresa(Empresa empresa) {
		  
		return repository.findByEmpresa(empresa);  
	}
	 
	@Transactional 
	public void alterarEmpresa(EnderecoEmpresa enderecoEmpresa) {
		repository.save(enderecoEmpresa);
	}

	public void excluirEnderecoEmpresa(EnderecoEmpresa enderecoEmpresa) {
		empresaService.validaEmpresa(enderecoEmpresa.getEmpresa());
	    repository.delete(enderecoEmpresa);
		
	}
	
 
}
