package com.br.smallmanager.apismallManager.service;

import java.util.Date;
import java.util.List;
import java.util.Optional; 
import javax.transaction.Transactional; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.br.smallmanager.apismallManager.constants.Status;
import com.br.smallmanager.apismallManager.entity.Agendamento;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.AgendamentoRepository;
 

@Service
public class AgendamentoService {

	@Autowired
	private AgendamentoRepository repository;
	 
	@Transactional 
	public Agendamento cadastrarAgendamento(Agendamento Agendamento ) { 
	  return repository.save(Agendamento);
		 
	}
	  
	@Transactional 
	public List<Agendamento> buscar(Agendamento AgendamentoFiltro) {
		Example<Agendamento> example = Example.of(AgendamentoFiltro, ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		 
		return repository.findAll(example);
	}
	
	@Transactional 
	public Optional<Agendamento> buscarPorId(Agendamento Agendamento) {
	 
		 if	(Agendamento.getId() != null) {
			 return repository.findById(Agendamento.getId());
		 }else {
			 throw new RegraNegocioException("Agendamento não encontrada.");
		 } 
	}
 
	public void alterarAgendamento(Agendamento Agendamento) {
		repository.save(Agendamento);
	}
	@Transactional 
	public void listarAgendamentos() {
		repository.findAll();
	}
	
	@Transactional 
	public void listarAgendamentosStatus(String status) {
		repository.findByStatus(status);
	}

	@Transactional 
	public void listarAgendamentosEmpresa(Empresa empresa) {
		repository.findByEmpresa(empresa);
	}
	
	@Transactional 
	public void listarAgendamentosData(Date date) {
		repository.findByData_inicio(date);
	}
	
	public Agendamento atualizarStatus(String status, Long idAgenda) {
		
		 Agendamento ageUpdate = new Agendamento();
		 
		if (obterPorId(idAgenda).isPresent()) {
			ageUpdate = obterPorId(idAgenda).get();  
			ageUpdate.setId(ageUpdate.getId());
			ageUpdate.setStatus(status.toString());
			return repository.save(ageUpdate);
		}else
			throw new RegraNegocioException("Agenda não encontrada.");
		  
	}
	
	@Transactional 
	public Optional<Agendamento> obterPorId(Long id) {
		return repository.findById(id);
	} 
	
	public void excluirAgendamento(Agendamento Agendamento) {
	  repository.delete(Agendamento);
	}
	
	public void validaAgendamento(Agendamento Agendamento) {
		 
		if (repository.findById(Agendamento.getId()).isPresent() == false) {
			throw new RegraNegocioException("Agendamento não existe !");
		}
	}
}
