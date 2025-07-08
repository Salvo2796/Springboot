package com.azienda.jpa.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
@Entity
@Table(name = "permessi")
public class Permesso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_di_permesso", nullable = false)
    private TipoPermesso tipoPermesso;

    @JsonIgnore
    @OneToMany(mappedBy="permesso", fetch=FetchType.LAZY)
    private List<Account> account=new ArrayList<Account>();

    public Permesso(TipoPermesso tipoPermesso, List<Account> account) {
        this.tipoPermesso = tipoPermesso;
        this.account = account;
    }

    public Permesso(TipoPermesso tipoPermesso) {
        this.tipoPermesso = tipoPermesso;
    }


    public Permesso() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TipoPermesso getTipoPermesso() {
        return tipoPermesso;
    }

    public void setTipoPermesso(TipoPermesso tipoPermesso) {
        this.tipoPermesso = tipoPermesso;
    }

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Permesso permesso = (Permesso) o;
        return id == permesso.id && tipoPermesso == permesso.tipoPermesso && Objects.equals(account, permesso.account);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoPermesso, account);
    }

    @Override
    public String toString() {
        return "Permesso{" +
                "id=" + id +
                ", tipoPermesso=" + tipoPermesso +
                ", account=" + account +
                '}';
    }
}
