package com.br.smallmanager.apismallManager.service;

import java.util.List; 
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.br.smallmanager.apismallManager.entity.Fotos;
import com.br.smallmanager.apismallManager.repository.FotoRepository;

@Service
public class FotoService {

	@Autowired	
	private FotoRepository repository;
	
	
	 
	private String raiz = "C:\\testeupload";
	 
	 
	private String diretorioFotos = "\\fotos";
	
	@Transactional 
	public Fotos adicioanarFoto(Fotos foto) {
		 
		return repository.save(foto);
			 
	}
	
	@Transactional 
	public void deletarFoto(Fotos foto) {
		  repository.delete(foto);
			 
	}
	
	@Transactional 
	public List<Fotos> buscarContatoEmpresa() {
		return repository.findAll();  
	}
	 
}
