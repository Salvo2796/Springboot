package com.azienda.service.interfaces;

import com.azienda.jpa.entity.Progetto;
import org.json.JSONObject;

import java.util.List;

public interface ProgettoService {
    public void insertProgetto(Progetto progetto);
    public Progetto convertJsonObject(JSONObject j);
    public Progetto findProgettoByNome(String nome);

    public List<Progetto> findProgettoByBudget(double budget);
    public void deleteProgetto(Progetto progetto);
}
