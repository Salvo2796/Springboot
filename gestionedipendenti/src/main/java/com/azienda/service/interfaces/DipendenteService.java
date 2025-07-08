package com.azienda.service.interfaces;


import java.time.LocalDate;
import java.util.List;

import org.json.JSONObject;

import com.azienda.jpa.entity.Dipendente;

public interface DipendenteService {

    public void insertDipendente(Dipendente d);

    public Dipendente convertJSONDipendente(JSONObject j);

    public List<Dipendente> findAllDipendente();

    public Dipendente findByCf(String cf);

    public List<Dipendente> findByStipendio(double stipendio);

    public List<Dipendente> findByStipendioAndDataDiAssunzione(double stipendio, LocalDate dataDiAssunzione);

    public void deleteDipendente(Dipendente d);

    public void updateDipendente(Dipendente d);

    public void insertDipendenteAccount(Dipendente d);

    public Dipendente convertJSONDipendenteConAccount(JSONObject j);

    public void updateDipendenteAccount(Dipendente d);

}
