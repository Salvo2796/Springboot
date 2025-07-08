package com.azienda.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "progetti", uniqueConstraints = @UniqueConstraint(columnNames = "nome"))
public class Progetto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descrizione")
    private String descrizione;

    @Column(name = "data_inizio", nullable = false)
    private LocalDate dataInizio;

    @Column(name = "data_fine", nullable = false)
    private LocalDate dataFine;

    @Column(name = "budget", nullable = false)
    private double budget;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "partecipazioni",
            joinColumns = @JoinColumn(name = "progetto_id"),
            inverseJoinColumns = @JoinColumn(name = "dipendente_id")
    )
    private Set<Dipendente> dipendenti = new HashSet<>();

    public Progetto() {}

    public Progetto(String nome, String descrizione, LocalDate dataInizio, LocalDate dataFine, double budget, Set<Dipendente> dipendenti) {
        this.nome = nome;
        this.descrizione = descrizione;
        this.dataInizio = dataInizio;
        this.dataFine = dataFine;
        this.budget = budget;
        this.dipendenti = dipendenti;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public LocalDate getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(LocalDate dataInizio) {
        this.dataInizio = dataInizio;
    }

    public LocalDate getDataFine() {
        return dataFine;
    }

    public void setDataFine(LocalDate dataFine) {
        this.dataFine = dataFine;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public Set<Dipendente> getDipendenti() {
        return dipendenti;
    }

    public void setDipendenti(Set<Dipendente> dipendenti) {
        this.dipendenti = dipendenti;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Progetto progetto = (Progetto) o;
        return id == progetto.id && Double.compare(budget, progetto.budget) == 0 && Objects.equals(nome, progetto.nome) && Objects.equals(descrizione, progetto.descrizione) && Objects.equals(dataInizio, progetto.dataInizio) && Objects.equals(dataFine, progetto.dataFine) && Objects.equals(dipendenti, progetto.dipendenti);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descrizione, dataInizio, dataFine, budget, dipendenti);
    }

    @Override
    public String toString() {
        return "Progetto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descrizione='" + descrizione + '\'' +
                ", dataInizio=" + dataInizio +
                ", dataFine=" + dataFine +
                ", budget=" + budget +
                ", dipendenti=" + dipendenti +
                '}';
    }
}
