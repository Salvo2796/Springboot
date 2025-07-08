package com.azienda.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Entity
@Table(name = "dipendenti")
public class Dipendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cognome", nullable = false)
    private String cognome;

    @Column(name = "cf", nullable = false, unique = true, length = 16, columnDefinition = "char(16)")
    private String cf;

    @Column(name = "data_di_nascita", nullable = false)
    private LocalDate dataDiNascita;

    @Column(name = "data_di_assunzione", nullable = false)
    private LocalDate dataDiAssunzione;

    @Column(name = "stipendio", nullable = false)
    private double stipendio;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_account",referencedColumnName = "id")
    private Account account;

    @ManyToMany(mappedBy = "dipendenti")
    private Set<Progetto> progetti = new HashSet<>();

    public Dipendente() {
    }

    public Dipendente(String nome, String cognome, String cf, LocalDate dataDiNascita, LocalDate dataDiAssunzione, double stipendio, Account account, Set<Progetto> progetti) {
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.dataDiNascita = dataDiNascita;
        this.dataDiAssunzione = dataDiAssunzione;
        this.stipendio = stipendio;
        this.account = account;
        this.progetti = progetti;
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

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public LocalDate getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(LocalDate dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public LocalDate getDataDiAssunzione() {
        return dataDiAssunzione;
    }

    public void setDataDiAssunzione(LocalDate dataDiAssunzione) {
        this.dataDiAssunzione = dataDiAssunzione;
    }

    public double getStipendio() {
        return stipendio;
    }

    public void setStipendio(double stipendio) {
        this.stipendio = stipendio;
    }


    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Set<Progetto> getProgetti() {
        return progetti;
    }

    public void setProgetti(Set<Progetto> progetti) {
        this.progetti = progetti;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Dipendente that = (Dipendente) o;
        return id == that.id && Double.compare(stipendio, that.stipendio) == 0 && Objects.equals(nome, that.nome) && Objects.equals(cognome, that.cognome) && Objects.equals(cf, that.cf) && Objects.equals(dataDiNascita, that.dataDiNascita) && Objects.equals(dataDiAssunzione, that.dataDiAssunzione) && Objects.equals(account, that.account) && Objects.equals(progetti, that.progetti);
    }

    @Override
public int hashCode() {
    return Objects.hash(cf);  // usa solo il codice fiscale o altro identificatore unico
}

    @Override
    public String toString() {
        return "Dipendente{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", cf='" + cf + '\'' +
                ", dataDiNascita=" + dataDiNascita +
                ", dataDiAssunzione=" + dataDiAssunzione +
                ", stipendio=" + stipendio +
                ", account=" + account +
                ", progetti=" + progetti +
                '}';
    }
}
