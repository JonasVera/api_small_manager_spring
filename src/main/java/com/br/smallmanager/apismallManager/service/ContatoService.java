package com.br.smallmanager.apismallManager.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
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
	public List<Contato> buscarContatoEmpresa(Contato contatoFiltro) {
		Example<Contato> example = Example.of(contatoFiltro, ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		 
		return repository.findAll(example);
	}
	
	@Transactional 
	public void excluirEmpresa(Contato contato) {
		  repository.delete(contato);
	 }
		
	@Transactional 
	public void alterarEmpresa(Contato contato) {
		repository.save(contato);
	}
}
