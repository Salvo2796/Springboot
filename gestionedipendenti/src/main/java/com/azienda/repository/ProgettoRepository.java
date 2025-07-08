package com.azienda.repository;

import com.azienda.jpa.entity.Progetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Integer> {
    public Progetto findByNomeContaining(String nome);
    public List<Progetto> findByBudgetIsGreaterThanEqual(double budget);
}
