package com.azienda.repository;

import com.azienda.jpa.entity.Progetto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProgettoRepository extends JpaRepository<Progetto, Integer> {
    public Optional<Progetto> findByNome(String nome);

    public List<Progetto> findByBudgetIsGreaterThanEqual(double budget);
}
