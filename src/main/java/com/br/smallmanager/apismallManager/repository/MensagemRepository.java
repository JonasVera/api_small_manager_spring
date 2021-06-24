package com.br.smallmanager.apismallManager.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.Mensagem;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	
 List<Mensagem> findByEmpresa(Empresa empresa);
  
 List<Mensagem> findByEmailAndTipo(Mensagem msg);
}
