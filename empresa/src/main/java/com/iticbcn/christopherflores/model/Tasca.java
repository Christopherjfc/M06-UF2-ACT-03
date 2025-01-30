package com.iticbcn.christopherflores.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Tasca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTasca;
    @Column
    private String nomTasca;
    @Column
    private String descripcio;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY,mappedBy = "tasca")
    private Set<Empleat> empleat = new HashSet<>();

    public Tasca(String nomTasca, String descripcio) {
        this.nomTasca = nomTasca;
        this.descripcio = descripcio;
    }

    public void addEmpleat(Empleat empleat) {
        if (!this.empleat.contains(empleat)) {
            this.empleat.add(empleat);
        }
        empleat.getTasca().add(this);
    }

    public int getIdTasca() {
        return idTasca;
    }

    public void setIdTasca(int idTasca) {
        this.idTasca = idTasca;
    }

    public String getNomTasca() {
        return nomTasca;
    }

    public void setNomTasca(String nomTasca) {
        this.nomTasca = nomTasca;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

    public Set<Empleat> getEmpleat() {
        return empleat;
    }

    public void setEmpleat(Set<Empleat> empleat) {
        this.empleat = empleat;
    }

}
