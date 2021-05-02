package com.br.smallmanager.apismallManager.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.smallmanager.apismallManager.entity.CategoriaProduto;
import com.br.smallmanager.apismallManager.entity.Usuario;

@Repository
public interface CategoriaRepository extends JpaRepository<CategoriaProduto, Long> {
	 
}
