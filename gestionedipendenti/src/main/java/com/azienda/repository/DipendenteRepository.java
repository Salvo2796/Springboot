package com.azienda.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.azienda.jpa.entity.Dipendente;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DipendenteRepository extends JpaRepository <Dipendente,Integer> {

    public Dipendente findDipendenteBycf(String cf);

    public List<Dipendente> findDipendentiByStipendioGreaterThanEqual(double stipendio);

    public List<Dipendente> findDipendenteByStipendioGreaterThanEqualAndDataDiAssunzioneIsAfter(double stipendio, LocalDate dataDiAssunzione);

}
