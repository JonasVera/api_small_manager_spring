package com.br.smallmanager.apismallManager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.br.smallmanager.apismallManager.entity.Contato;
import com.br.smallmanager.apismallManager.entity.Empresa;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

	 List<Contato> findByEmpresa(Empresa empresa);
}
