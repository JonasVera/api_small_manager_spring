package com.br.smallmanager.apismallManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.smallmanager.apismallManager.entity.Produto;
 
@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

}
