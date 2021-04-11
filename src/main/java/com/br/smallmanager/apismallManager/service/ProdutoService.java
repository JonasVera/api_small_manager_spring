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
import com.br.smallmanager.apismallManager.entity.Produto;
import com.br.smallmanager.apismallManager.entity.Usuario;
import com.br.smallmanager.apismallManager.exeptions.RegraNegocioException;
import com.br.smallmanager.apismallManager.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repository;
	
	@Autowired 
	private UsuarioService userService;
	
	@Transactional 
	public Produto cadastrarProduto(Produto produto,Empresa usuario) {
		
		Optional<Usuario> user = userService.obterPorId(usuario.getId());
		
		if (!user.isEmpty()) {
				produto.setEmpresa(usuario);
				return repository.save(produto);
		}else {
			throw new RegraNegocioException("Não foi possivel cadastrar produto, empresa não encontrado.");
		} 
	}
	  
	@Transactional 
	public List<Produto> buscar(Produto produtoFiltro) {
		Example<Produto> example = Example.of(produtoFiltro, ExampleMatcher.matching()
				.withIgnoreCase()
				.withStringMatcher(StringMatcher.CONTAINING));
		 
		return repository.findAll(example);
	}
	
	@Transactional 
	public Optional<Produto> buscarPorId(Produto produto) {
	 
		 if	(produto.getEmpresa().getId() != null) {
			 return repository.findById(produto.getId());
		 }else {
			 throw new RegraNegocioException("Produto não encontrada para esta empresa.");
		 } 
	}
 
	public void alterarProduto(Produto produto) {
		repository.save(produto);
	}
	@Transactional 
	public void listarEmrpesa() {
		repository.findAll();
	}
	@Transactional 
	public Optional<Produto> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	 
	
	public void excluirProduto(Produto produto) {
	  repository.delete(produto);
	}
	
	public void validaProduto(Produto produto) {
		 
		if (repository.findById(produto.getId()).isPresent() == false) {
			throw new RegraNegocioException("Produto não existe !");
		}
	}
}
