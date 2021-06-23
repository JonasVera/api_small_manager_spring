package com.br.smallmanager.apismallManager.service; 
import com.br.smallmanager.apismallManager.entity.Orcamento;
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.OrcamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrcamentoService {

	@Autowired
	private OrcamentoRepository repository;
	@Autowired
	ProdutoService produtoService;
	@Transactional 
	public Orcamento cadastrarOrcamento(Orcamento orcamento) {
		
		Optional<Produto> produtoExistente = produtoService.obterPorId(orcamento.getProduto().getId());
		
		if (produtoExistente.isPresent() == true) { 
			return repository.save(orcamento);
				
		}else {
			throw new RegraNegocioException("Não foi possivel cadastrar um orcamento, produto não encontrada.");
		} 
	}
	
	@Transactional 
	public Optional<Orcamento> buscarPorId(Orcamento orcamento) { 
			 return repository.findById(orcamento.getId());
	}
	@Transactional 
	public List<Orcamento> buscarOrcamentoProduto(Produto produto) { 
		return repository.findByProduto(produto);  
	}
	 
	public void excluirEvento(Orcamento orcamento) { 
	    repository.delete(orcamento); 
	} 
	 
	public Orcamento alterarOrcamento(Orcamento orcamento) {
		if(orcamento.getId() != null || orcamento.getId() != 0) {
			return repository.save(orcamento);
		}else {
			throw new RegraNegocioException("Não foi possivel cadastrar um orcamento, orçamento não encontrado.");
		}
		
	}
}
