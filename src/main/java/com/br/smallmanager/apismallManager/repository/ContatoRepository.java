package com.br.smallmanager.apismallManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.smallmanager.apismallManager.entity.Contato;
import com.br.smallmanager.apismallManager.entity.Empresa;

@Repository
public interface ContatoRepository extends JpaRepository<Contato, Long> {

	 List<Contato> findByEmpresa(Empresa empresa);
}
