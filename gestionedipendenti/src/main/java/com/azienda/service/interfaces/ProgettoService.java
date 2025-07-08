package com.azienda.service.interfaces;

import com.azienda.jpa.entity.Progetto;
import org.json.JSONObject;

import java.util.List;
import java.util.Optional;

public interface ProgettoService {
    public void insertProgetto(Progetto progetto);
    public Progetto convertJsonObject(JSONObject j);
    public Optional<Progetto> findByNome(String nome);

    public List<Progetto> findProgettoByBudget(double budget);
    public void deleteProgetto(Optional<Progetto> p);
}
