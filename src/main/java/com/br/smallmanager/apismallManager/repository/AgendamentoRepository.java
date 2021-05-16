package com.br.smallmanager.apismallManager.repository;
 
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.smallmanager.apismallManager.entity.Agendamento;
import com.br.smallmanager.apismallManager.entity.Empresa;
  
@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, Long> {
	
	 List<Agendamento> findByStatus(String status);
	 List<Agendamento> findByEmpresa(Empresa empresa);
	 List<Agendamento> findByData_inicio(Date data_inicio);
	 
	 
}
