package com.azienda.repository;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.TipoPermesso;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    public Account findDipendenteByEmail(String email);
    public List<Account> findByPermessoTipoPermesso(TipoPermesso tipoPermesso);
}
