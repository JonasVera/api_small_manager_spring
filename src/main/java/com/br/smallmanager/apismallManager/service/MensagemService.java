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
import com.br.smallmanager.apismallManager.entity.Mensagem;
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.MensagemRepository;

@Service
public class MensagemService {

	@Autowired
	private MensagemRepository repository;
	 
	@Transactional 
	public Mensagem cadastrarMensagem(Mensagem Mensagem ) { 
	  return repository.save(Mensagem);
	}
	  
	@Transactional 
	public List<Mensagem> buscar(Mensagem MensagemFiltro) {
		Example<Mensagem> example = Example.of(MensagemFiltro, ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		 
		return repository.findAll(example);
	}
	
	@Transactional 
	public Optional<Mensagem> buscarPorId(Mensagem Mensagem) {
	 
		 if	(Mensagem.getId() != null) {
			 return repository.findById(Mensagem.getId());
		 }else {
			 throw new RegraNegocioException("Mensagem não encontrada.");
		 } 
	}
 
	public void alterarMensagem(Mensagem Mensagem) {
		repository.save(Mensagem);
	}
	@Transactional 
	public void listarMensagem() {
		repository.findAll();
	}
	@Transactional 
	public Optional<Mensagem> obterPorId(Long id) {
		return repository.findById(id);
	} 
	
	public void excluirMensagem(Mensagem Mensagem) {
	  repository.delete(Mensagem);
	}
	@Transactional 
	public List<Mensagem> mensagemPorEmpresa(Empresa empresa) {
		return repository.findByEmpresa(empresa);
	}
	
	public List<Mensagem> conversaTipo(Mensagem msg) {
		return repository.findByEmailAndTipo(msg);
	}
	
	public void validaMensagem(Mensagem Mensagem) {
		 
		if (repository.findById(Mensagem.getId()).isPresent() == false) {
			throw new RegraNegocioException("Mensagem não existe !");
		}
	}
}
