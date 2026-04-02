package com.azienda.service.interfaces;
import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;

import java.util.List;

public interface PermessoService {

    public Permesso savePermesso(Permesso permesso);

    Permesso findByTipoPermesso(TipoPermesso tipoPermesso);

    public List<Account> findDipendenteByPermesso(TipoPermesso tipoPermesso);

}
