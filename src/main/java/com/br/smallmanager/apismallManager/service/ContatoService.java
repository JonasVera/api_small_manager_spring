package com.br.smallmanager.apismallManager.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.smallmanager.apismallManager.entity.Contato;
import com.br.smallmanager.apismallManager.entity.Empresa; 
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.ContatoRepository;

@Service
public class ContatoService {
	
	@Autowired
	private ContatoRepository repository;
	@Autowired
	private EmpresaService empresaService;

	@Transactional 
	public Contato cadastrarContato(Contato contato) {
		
		Optional<Empresa> empresaExistente = empresaService.obterPorId(contato.getEmpresa().getId());
		
		if (!empresaExistente.isEmpty()) {
			contato.setEmpresa(empresaExistente.get() );
			return repository.save(contato);
				
		}else {
			throw new RegraNegocioException("Não foi possivel cadastrar um contado, empresa não encontrada.");
		} 
	}
	
	@Transactional 
	public Optional<Contato> buscarPorId(Contato contato) {
	 
		 if	(contato.getEmpresa().getId() != null) {
			 return repository.findById(contato.getId());
		 }else {
			 throw new RegraNegocioException("Contato não encontrado para esta empresa.");
		 } 
	}
	 
	@Transactional 
	public Optional<Contato> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	@Transactional 
	public List<Contato> buscarContatoEmpresa(Empresa empresa) {
		  
		return repository.findByEmpresa(empresa);  
	}
	 
	@Transactional 
	public void alterarEmpresa(Contato contato) {
		repository.save(contato);
	}

	public void excluirContato(Contato contato) {
		empresaService.validaEmpresa(contato.getEmpresa());
	    repository.delete(contato); 
	}
	
 
}
