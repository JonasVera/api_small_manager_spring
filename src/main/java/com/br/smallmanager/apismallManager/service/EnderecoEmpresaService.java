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
		
		Optional<Empresa> empresaExistente = empresaService.obterPorId(enderecoEmpresa.getEmpresa().getId());
		System.out.println("ID END"+enderecoEmpresa.getEmpresa().getId());	
		if (!empresaExistente.isPresent() == true) {
			enderecoEmpresa.setEmpresa(empresaExistente.get() );
			
			return repository.save(enderecoEmpresa);
			
		}else {
			throw new RegraNegocioException("Não foi possivel cadastrar um contado, empresa não encontrada.");
		} 
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
