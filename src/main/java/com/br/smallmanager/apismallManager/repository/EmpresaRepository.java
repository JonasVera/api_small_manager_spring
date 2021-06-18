package com.br.smallmanager.apismallManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.Usuario;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>  {
	 List<Empresa> findByUsuario(Usuario usuario);
	 List<Empresa> findByNome(String nome);
	  
}
	