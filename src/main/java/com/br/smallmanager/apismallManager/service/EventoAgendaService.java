package com.br.smallmanager.apismallManager.service;

import java.util.List;
import java.util.Optional; 
import javax.transaction.Transactional; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.EventoAgenda;
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.EventoAgendaRepositoy;
  
@Service
public class EventoAgendaService {

	@Autowired
	private  EventoAgendaRepositoy repository;
	@Autowired
	ProdutoService produtoService;
	@Transactional 
	public EventoAgenda cadastrarEvento(EventoAgenda evento) {
		
		Optional<Produto> produtoExistente = produtoService.obterPorId(evento.getProduto().getId());
		
		if (!produtoExistente.isPresent() == true) {
			
			return repository.save(evento);
				
		}else {
			throw new RegraNegocioException("Não foi possivel cadastrar um evento, produto não encontrada.");
		} 
	}
	
	@Transactional 
	public Optional<EventoAgenda> buscarPorId(EventoAgenda evento) { 
			 return repository.findById(evento.getId());
	}
	@Transactional 
	public List<EventoAgenda> buscarAgendaProduto(Produto produto) { 
		return repository.findByProduto(produto);  
	}
	 
	public void excluirEvento(EventoAgenda evento) { 
	    repository.delete(evento); 
	} 
	
	@Transactional 
	public void alterarEmpresa(EventoAgenda evento) {
		repository.save(evento);
	}
}
