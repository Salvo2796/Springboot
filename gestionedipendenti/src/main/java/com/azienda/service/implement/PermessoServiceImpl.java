package com.azienda.service.implement;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import com.azienda.repository.PermessoRepository;
import com.azienda.service.interfaces.PermessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermessoServiceImpl implements PermessoService {

    @Autowired
    private PermessoRepository permessoRepository;

    @Override
    public List<Permesso> findByPermessoByTipoPermesso(TipoPermesso tipoPermesso) {
        return permessoRepository.findPermessoByTipoPermesso(tipoPermesso);
    }

    @Override
    public List<Account> findDipendenteByPermesso(TipoPermesso tipoPermesso) {
        Permesso permesso = permessoRepository.findByTipoPermesso(tipoPermesso);
        if (permesso != null) {
            return permesso.getAccount();
        } else {
            return List.of();
        }
    }



    @Override
    public Permesso savePermesso(Permesso permesso) {
        return permessoRepository.save(permesso);
    }
}

