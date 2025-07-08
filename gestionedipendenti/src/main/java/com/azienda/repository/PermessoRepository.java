package com.azienda.repository;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Dipendente;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermessoRepository extends JpaRepository<Permesso, Integer> {

    public List<Permesso> findPermessoByTipoPermesso(TipoPermesso tipoPermesso);

    public Permesso findByTipoPermesso(TipoPermesso tipoPermesso);


}

