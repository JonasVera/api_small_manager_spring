package com.br.smallmanager.apismallManager.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.stereotype.Service;

import com.br.smallmanager.apismallManager.constants.Status;
import com.br.smallmanager.apismallManager.entity.CategoriaProduto;
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
	private EmpresaService userService;
	
	@Autowired 
	private CategoriaService categoriaService;
	
	
	@Transactional 
	public Produto cadastrarProduto(Produto produto) {
		
		Optional<Empresa> empresa = userService.obterPorId(produto.getEmpresa().getId());
		Optional<CategoriaProduto> categoria = categoriaService.obterPorId(produto.getCategoriaProduto().getId());
		 
		if (empresa.isPresent() == false)
			throw new RegraNegocioException("Não foi possivel cadastrar produto, empresa não encontrado.");
		else if  (categoria.isPresent() == false)
			throw new RegraNegocioException("Não foi possivel cadastrar produto, categoria não encontrada.");
		else 
			return repository.save(produto);
		 
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
 
	public List<Produto> listProdutos (){
		return repository.findAll();
	}
	
	public Produto ativarDesativar(Boolean status, Long idProduto) {
		
		Produto produtoUpdate = new Produto(); 
		
		if (obterPorId(idProduto).isPresent()) {
			produtoUpdate = obterPorId(idProduto).get();
			produtoUpdate.setData_cadastro(produtoUpdate.getData_cadastro());
			if (produtoUpdate.getFotos() != null) {
				produtoUpdate.setFotos(produtoUpdate.getFotos());
			}
			
			produtoUpdate.setId(produtoUpdate.getId());
			produtoUpdate.setStatus(status);
			return repository.save(produtoUpdate);
		}else
			throw new RegraNegocioException("Produto não encontrado.");
		  
	}
	
	public void alterarProduto(Produto produto) {
		Produto produtoUpdate = new Produto(); 
		
		if (obterPorId(produto.getId()).isPresent()) {
			produtoUpdate = obterPorId(produto.getId()).get();
			produto.setData_cadastro(produtoUpdate.getData_cadastro());
			if (produtoUpdate.getFotos() != null) {
				produto.setFotos(produtoUpdate.getFotos());
			}
			
			produtoUpdate.setId(produtoUpdate.getId());
			repository.save(produto);
		}else
			throw new RegraNegocioException("Produto não encontrado.");
		  
	}
	 
	@Transactional 
	public Optional<Produto> obterPorId(Long id) {
		return repository.findById(id);
	}
	
	@Transactional 
	public List<Produto> obterPorNome(String nome) {
		return repository.findByNome(nome);
	}
	
	@Transactional 
	public List<Produto> obterPorCategoria(CategoriaProduto categoria) {
		return repository.findByCategoriaProduto(categoria);
	}
	
	

	@Transactional 
	public List<Produto> produtoEmpresa(Empresa empresa) {
		return repository.findByEmpresa(empresa);
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
