package com.br.smallmanager.apismallManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.EventoAgenda;
import com.br.smallmanager.apismallManager.entity.Produto;

@Repository
public interface EventoAgendaRepositoy  extends JpaRepository<EventoAgenda, Long> { 
	 List<EventoAgenda> findByProduto(Produto produto);
	 
}
