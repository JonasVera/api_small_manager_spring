package com.br.smallmanager.apismallManager.repository;
 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.br.smallmanager.apismallManager.entity.Empresa;
import com.br.smallmanager.apismallManager.entity.Mensagem;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
	
 List<Mensagem> findByEmpresa(Empresa empresa);
 
 @Query(value = "select M from Mensagem as M where M.email = :email and M.tipo = :tipo")
 List<Mensagem> findByEmailAndTipo(String email, String tipo);
}
