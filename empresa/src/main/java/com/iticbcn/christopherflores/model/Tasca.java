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
import jakarta.persistence.OneToMany;

@Entity
public class Tasca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idTasca;
    @Column(nullable = false)
    private String nomTasca;
    @Column(nullable = false)
    private String descripcio;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,mappedBy = "tasca")
    private Set<Empleat> empleat = new HashSet<>();

    @OneToMany 
    (mappedBy = "tasca", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Historic> historics;


    public Tasca() {}
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

    public void addhsitoric(Historic historic){
        historics.add(historic);
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

    public Set<Historic> getHistorics() {
        return historics;
    }

    public void setHistorics(Set<Historic> historics) {
        this.historics = historics;
    }
    @Override
    public String toString() {
        return "ID=" + idTasca + ", Título=" + nomTasca + ", Descripción=" + descripcio;
    }

    
}
