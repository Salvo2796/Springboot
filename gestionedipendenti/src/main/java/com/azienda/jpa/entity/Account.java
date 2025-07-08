package com.azienda.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import java.util.Objects;

//Previene cicli infiniti nel JSON
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "email", nullable = false, unique = true)
    private String email;


    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL) //"account" è il nome del campo nella classe Dipendente che gestisce la relazione.
    private Dipendente dipendente;

    @ManyToOne
    @JoinColumn(name="id_permesso", referencedColumnName="id")
    private Permesso permesso;

    public Account() {
    }

    public Account(String username, String pass, String email, Dipendente dipendente, Permesso permesso) {
        this.username = username;
        this.pass = pass;
        this.email = email;
        this.dipendente = dipendente;
        this.permesso = permesso;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Dipendente getDipendente() {
        return dipendente;
    }

    public void setDipendente(Dipendente dipendente) {
        this.dipendente = dipendente;
    }

    public Permesso getPermesso() {
        return permesso;
    }

    public void setPermesso(Permesso permesso) {
        this.permesso = permesso;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id == account.id && Objects.equals(username, account.username) && Objects.equals(pass, account.pass) && Objects.equals(email, account.email) && Objects.equals(dipendente, account.dipendente) && Objects.equals(permesso, account.permesso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, pass, email, dipendente, permesso);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", pass='" + pass + '\'' +
                ", email='" + email + '\'' +
                ", dipendente=" + dipendente +
                ", permesso=" + permesso +
                '}';
    }
}
