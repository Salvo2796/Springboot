package com.azienda.service.implement;

import com.azienda.jpa.entity.Progetto;
import com.azienda.repository.ProgettoRepository;
import com.azienda.service.interfaces.ProgettoService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProgettoServiceImpl implements ProgettoService {

    @Autowired
    private ProgettoRepository pr;

    @Override
    public void insertProgetto(Progetto progetto) {
        pr.save(progetto);
    }

    @Override
    public Progetto convertJsonObject(JSONObject j) {
        String nome = j.getString("nome");
        String descrizione = j.optString("descrizione");
        LocalDate dataInizio = LocalDate.parse(j.getString("dataInizio"));
        LocalDate dataFine = LocalDate.parse(j.getString("dataFine"));
        double budget = j.optDouble("budget");

        return new Progetto(nome, descrizione, dataInizio,dataFine,budget,null);
    }

    @Override
    public Progetto findProgettoByNome(String nome) {
        return pr.findByNomeContaining(nome);
    }


    @Override
    public List<Progetto> findProgettoByBudget(double budget) {
        return pr.findByBudgetIsGreaterThanEqual(budget);
    }

    @Override
    public void deleteProgetto(Progetto progetto) {
        pr.delete(progetto);
    }
}
