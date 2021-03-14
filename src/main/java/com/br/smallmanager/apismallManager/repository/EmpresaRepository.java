package com.br.smallmanager.apismallManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.smallmanager.apismallManager.entity.Empresa;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long>  {

}
