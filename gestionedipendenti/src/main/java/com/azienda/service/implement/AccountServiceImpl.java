package com.azienda.service.implement;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Dipendente;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import com.azienda.repository.AccountRepository;
import com.azienda.service.interfaces.AccountService;
import com.azienda.service.interfaces.PermessoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository ar;

    @Override
    public void insertAccount(Account a) {
        ar.save(a);
    }

    @Override
    public Account convertJSONAccount(JSONObject j) {
        String username = j.getString("username");
        String pass = j.getString("pass");
        String email = j.getString("email");

        // Recupero tipo di permesso dal JSON
        Permesso permesso = null;

        if (j.has("tipo_di_permesso")) {
            String tipoPermessoStr = j.getString("tipo_di_permesso").toUpperCase();
            try {
                TipoPermesso tipoPermesso = TipoPermesso.valueOf(tipoPermessoStr);
                permesso = new Permesso(tipoPermesso);  // Creo permesso direttamente (senza DB qui)
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo di permesso non valido: " + tipoPermessoStr);
            }
        }

        // Creo e ritorno l'Account
        return new Account(username, pass, email, null, permesso);
    }


    @Override
    public List<Account> findAllAccount() {
        return ar.findAll();
    }

    @Override
    public Account findByEmail(String email) {
        return ar.findDipendenteByEmail(email);
    }

    @Override
    public void deleteAccount(Account a) {
        ar.delete(a);
    }

    @Override
    public void updateAccount(Account a) {
        ar.save(a);
    }

    @Override
    public Account convertJSONAccountConDipendente(JSONObject j) {
        String username = j.getString("username");
        String pass = j.getString("pass");
        String email = j.getString("email");

        Account account = new Account(username, pass, email, null,null);//Metto null perchè il dipendente lo creo dopo,quindi lo settiamo dopo

        if (j.has("dipendente")) {
            JSONObject d = j.getJSONObject("dipendente");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            String nome = d.getString("nome");
            String cognome = d.getString("cognome");
            String cf = d.getString("cf");
            LocalDate dataDiNascita = LocalDate.parse(d.getString("data_di_nascita"), formatter);
            LocalDate dataDiAssunzione = LocalDate.parse(d.getString("data_di_assunzione"), formatter);
            double stipendio = d.getDouble("stipendio");

            Dipendente dipendente = new Dipendente(nome, cognome, cf, dataDiNascita, dataDiAssunzione, stipendio,account,null);

            // Collego l'account al dipendente
            if (dipendente != null) {
                account.setDipendente(dipendente);
            }
            
        }

        if (j.has("tipo_di_permesso")) {
            String tipoString = j.getString("tipo_di_permesso").toUpperCase();

            try {
                TipoPermesso tipoPermesso = TipoPermesso.valueOf(tipoString);
                Permesso permesso = new Permesso(tipoPermesso);
                account.setPermesso(permesso);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Tipo di permesso non valido: " + tipoString);
            }
        }


        return account;

    }

    @Autowired
    private PermessoService permessoService;

    @Override
    public Account createAccountWithPermesso(Account account) {
        if (account.getPermesso() != null) {
            TipoPermesso tipoPermesso = account.getPermesso().getTipoPermesso();

            // Cerca il permesso esistente nel DB
            List<Permesso> permessi = permessoService.findByPermessoByTipoPermesso(tipoPermesso);

            if (!permessi.isEmpty()) {
                account.setPermesso(permessi.get(0));  // usa quello trovato
            } else {
                // se non esiste, crealo e salvalo
                Permesso nuovoPermesso = new Permesso(tipoPermesso);
                permessoService.savePermesso(nuovoPermesso);
                account.setPermesso(nuovoPermesso);
            }
        }

        return ar.save(account);
    }


}
