package com.azienda.repository;

import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PermessoRepository extends JpaRepository<Permesso, Integer> {

    Permesso findByTipoPermesso(TipoPermesso tipoPermesso);


}

