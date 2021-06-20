package com.br.smallmanager.apismallManager.repository;

import com.br.smallmanager.apismallManager.entity.Orcamento;
import com.br.smallmanager.apismallManager.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrcamentoRepository extends JpaRepository<Orcamento, Long> {
	 List<Orcamento> findByProduto(Produto produto);

}
