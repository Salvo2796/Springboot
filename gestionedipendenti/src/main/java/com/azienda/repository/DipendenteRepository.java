package com.azienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.azienda.jpa.entity.Dipendente;

import java.time.LocalDate;
import java.util.List;

public interface DipendenteRepository extends JpaRepository <Dipendente,Integer> {

    public Dipendente findDipendenteBycf(String cf);

    public List<Dipendente> findDipendentiByStipendioGreaterThanEqual(double stipendio);

    public List<Dipendente> findDipendenteByStipendioGreaterThanEqualAndDataDiAssunzioneIsAfter(double stipendio, LocalDate dataDiAssunzione);

}
