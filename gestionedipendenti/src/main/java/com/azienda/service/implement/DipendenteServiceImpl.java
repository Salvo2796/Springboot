package com.azienda.service.implement;

import com.azienda.jpa.entity.Account;
import com.azienda.jpa.entity.Permesso;
import com.azienda.jpa.entity.Progetto;
import com.azienda.jpa.entity.TipoPermesso;
import com.azienda.service.interfaces.PermessoService;
import com.azienda.service.interfaces.ProgettoService;
import jakarta.transaction.Transactional;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.azienda.jpa.entity.Dipendente;
import com.azienda.repository.DipendenteRepository;
import com.azienda.service.interfaces.DipendenteService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class DipendenteServiceImpl implements DipendenteService {
    @Autowired
    private DipendenteRepository dr;

    @Autowired
    private PermessoService permessoService;

    @Autowired
    private ProgettoService progettoService;

    @Transactional
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

        return new Dipendente(nome, cognome, cf, dataDiNascita, dataDiAssunzione, stipendio, null, null);
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
                TipoPermesso tipoPermesso = TipoPermesso.valueOf(tipoPermessoStr);
                List<Permesso> permessi = permessoService.findByPermessoByTipoPermesso(tipoPermesso);
                if (permessi != null && !permessi.isEmpty()) {
                    permesso = permessi.get(0);
                } else {
                    throw new RuntimeException("Permesso non trovato: " + tipoPermessoStr);
                }
            }

            account = new Account(username, pass, email, null, permesso);
        }

        // 1. Creo il dipendente senza progetti (set vuoto) e con account, ma ancora non
        // salvato
        Dipendente dipendente = new Dipendente(nome, cognome, cf, dataDiNascita, dataDiAssunzione, stipendio, account,
                new HashSet<>());

        // Associo account a dipendente
        if (account != null) {
            account.setDipendente(dipendente);
        }

        // 2. Salvo il dipendente prima di associare i progetti
        dipendente = dr.save(dipendente);

        // 3. Ora gestisco i progetti (se presenti)
        if (j.has("progetti")) {
            JSONArray progettiArray = j.getJSONArray("progetti");
            for (int i = 0; i < progettiArray.length(); i++) {
                JSONObject progettoJSON = progettiArray.getJSONObject(i);
                String nomeProgetto = progettoJSON.getString("nome");

                Optional<Progetto> progettoOpt = progettoService.findByNome(nomeProgetto);
                Progetto progetto;

                if (progettoOpt.isPresent()) {
                    progetto = progettoOpt.get();
                } else {
                    String descrizione = progettoJSON.optString("descrizione", "");
                    LocalDate dataInizio = LocalDate.parse(progettoJSON.getString("data_inizio"), formatter);
                    LocalDate dataFine = LocalDate.parse(progettoJSON.getString("data_fine"), formatter);
                    double budget = progettoJSON.getDouble("budget");

                    progetto = new Progetto(nomeProgetto, descrizione, dataInizio, dataFine, budget, new HashSet<>());
                    progettoService.insertProgetto(progetto);
                }

                // Aggiorno entrambi i lati della relazione
                progetto.getDipendenti().add(dipendente);
                dipendente.getProgetti().add(progetto);

                // Salvo progetto aggiornato (per aggiornare la relazione)
                progettoService.insertProgetto(progetto);
            }

            // 4. Salvo di nuovo il dipendente con i progetti aggiornati
            dipendente = dr.save(dipendente);
        }

        return dipendente;
    }

    @Override
    public void updateDipendenteAccount(Dipendente d) {
        dr.save(d);
    }

    @Override
    public Dipendente aggiungiProgettoADipendenteByCf(String cf, String nomeProgetto) {
        Dipendente dipendente = dr.findDipendenteBycf(cf);
        if (dipendente == null) {
            throw new RuntimeException("Dipendente con CF " + cf + " non trovato");
        }

        Optional<Progetto> progettoOpt = progettoService.findByNome(nomeProgetto);
        if (!progettoOpt.isPresent()) {
            throw new RuntimeException("Progetto con nome " + nomeProgetto + " non trovato");
        }
        Progetto progetto = progettoOpt.get();

        // Aggiungi progetto al dipendente e viceversa
        dipendente.getProgetti().add(progetto);
        progetto.getDipendenti().add(dipendente);

        // Salva entrambi
        dr.save(dipendente);
        progettoService.insertProgetto(progetto);

        return dipendente;
    }

}
