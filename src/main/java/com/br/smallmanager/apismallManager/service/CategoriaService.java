package com.br.smallmanager.apismallManager.service;

import java.util.List;
import java.util.Optional; 
import javax.transaction.Transactional; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service; 
import com.br.smallmanager.apismallManager.entity.CategoriaProduto; 
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repository;
	 
	@Transactional 
	public CategoriaProduto cadastrarCategoria(CategoriaProduto categoria ) { 
	  return repository.save(categoria);
		 
	}
	  
	@Transactional 
	public List<CategoriaProduto> buscar(CategoriaProduto categoriaFiltro) {
		Example<CategoriaProduto> example = Example.of(categoriaFiltro, ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		 
		return repository.findAll(example);
	}
	
	@Transactional 
	public Optional<CategoriaProduto> buscarPorId(CategoriaProduto categoria) {
	 
		 if	(categoria.getId() != null) {
			 return repository.findById(categoria.getId());
		 }else {
			 throw new RegraNegocioException("Categoria não encontrada.");
		 } 
	}
 
	public void alterarCategoria(CategoriaProduto categoria) {
		repository.save(categoria);
	}
	@Transactional 
	public void listarEmrpesa() {
		repository.findAll();
	}
	@Transactional 
	public Optional<CategoriaProduto> obterPorId(Long id) {
		return repository.findById(id);
	} 
	
	public void excluirCategoria(CategoriaProduto categoria) {
	  repository.delete(categoria);
	}
	
	public void validaCategoria(CategoriaProduto categoria) {
		 
		if (repository.findById(categoria.getId()).isPresent() == false) {
			throw new RegraNegocioException("Categoria não existe !");
		}
	}
}
