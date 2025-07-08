package com.azienda.service.implement;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.TipoPermesso;
import com.azienda.service.interfaces.PermessoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.azienda.jpa.entity.Dipendente;
import com.azienda.repository.DipendenteRepository;
import com.azienda.service.interfaces.DipendenteService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class DipendenteServiceImpl implements DipendenteService  {
    @Autowired
    private DipendenteRepository dr;

    @Autowired
    private PermessoService permessoService;


    @Override
    public void insertDipendente(Dipendente d) {
        dr.save(d);
    }

    @Override
    public Dipendente convertJSONDipendente(JSONObject j) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String nome = j.get("nome").toString();
        String cognome = j.get("cognome").toString();
        String cf = j.get("cf").toString();

        LocalDate dataDiNascita = LocalDate.parse(j.get("data_di_nascita").toString(), formatter);
        LocalDate dataDiAssunzione = LocalDate.parse(j.get("data_di_assunzione").toString(), formatter);

        double stipendio = Double.parseDouble(j.get("stipendio").toString());

        return new Dipendente(nome, cognome, cf, dataDiNascita, dataDiAssunzione, stipendio,null,null);
    }

    @Override
    public List<Dipendente> findAllDipendente() {
        return dr.findAll();
    }

    @Override
    public Dipendente findByCf(String cf) {
        return dr.findDipendenteBycf(cf);
    }

    @Override
    public List<Dipendente> findByStipendio(double stipendio) {
        return dr.findDipendentiByStipendioGreaterThanEqual(stipendio);
    }

    @Override
    public List<Dipendente> findByStipendioAndDataDiAssunzione(double stipendio, LocalDate dataDiAssunzione) {
        return dr.findDipendenteByStipendioGreaterThanEqualAndDataDiAssunzioneIsAfter(stipendio, dataDiAssunzione);
    }

    @Override
    public void deleteDipendente(Dipendente d) {
        dr.delete(d);
    }

    @Override
    public void updateDipendente(Dipendente d) {
        dr.save(d);
    }

    @Override
    public void insertDipendenteAccount(Dipendente d) {
        dr.save(d);
    }

    @Override
    public Dipendente convertJSONDipendenteConAccount(JSONObject j) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        String nome = j.getString("nome");
        String cognome = j.getString("cognome");
        String cf = j.getString("cf");
        LocalDate dataDiNascita = LocalDate.parse(j.getString("data_di_nascita"), formatter);
        LocalDate dataDiAssunzione = LocalDate.parse(j.getString("data_di_assunzione"), formatter);
        double stipendio = j.getDouble("stipendio");

        Account account = null;

        if (j.has("account")) {
            JSONObject accJ = j.getJSONObject("account");

            String username = accJ.getString("username");
            String pass = accJ.getString("pass");
            String email = accJ.getString("email");

            Permesso permesso = null;

            if (accJ.has("tipo_di_permesso")) {
                String tipoPermessoStr = accJ.getString("tipo_di_permesso").toUpperCase();
                try {
                    TipoPermesso tipoPermesso = TipoPermesso.valueOf(tipoPermessoStr);
                    // Recupera permesso dal DB tramite servizio
                    List<Permesso> permessi = permessoService.findByPermessoByTipoPermesso(tipoPermesso);
                    if (permessi != null && !permessi.isEmpty()) {
                        permesso = permessi.get(0); // Prendi il primo permesso trovato
                    } else {
                        throw new RuntimeException("Permesso non trovato nel DB per tipo: " + tipoPermessoStr);
                    }
                } catch (IllegalArgumentException e) {
                    throw new RuntimeException("Tipo di permesso non valido: " + tipoPermessoStr);
                }
            }

        


            // Creo l'account con il permesso (può essere anche null)
            account = new Account(username, pass, email, null, permesso);
        }

        // Creo Dipendente
        Dipendente dipendente = new Dipendente(nome, cognome, cf, dataDiNascita, dataDiAssunzione, stipendio, account,null);

        // Collegamento bidirezionale
        if (account != null) {
            account.setDipendente(dipendente);
        }

        return dipendente;
    }


    @Override
    public void updateDipendenteAccount(Dipendente d) {
        dr.save(d);
    }

}
