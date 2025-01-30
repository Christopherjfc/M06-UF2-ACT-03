package com.iticbcn.christopherflores.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Departament")
public class Departament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idDepartament;
    @Column
    private String nomDepartament;

    // DEPARTAMENTO -> contiene -> EMPRESA
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idEmpresa", foreignKey = @ForeignKey(name = "FK_DEP_EMPRESA"), nullable = false)
    private Empresa empresa;

    // EMPLEADO -> contiene -> DEPARTAMENTO
    @OneToMany (mappedBy = "departament", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Empleat> empleats;

    // constructor
    public Departament(String nomDepartament, Empresa empresa) {
        this.nomDepartament = nomDepartament;
        this.empresa = empresa;
    }

    public int getIdDepartament() {
        return idDepartament;
    }

    public void setIdDepartament(int idDepartament) {
        this.idDepartament = idDepartament;
    }

    public String getNomDepartament() {
        return nomDepartament;
    }

    public void setNomDepartament(String nomDepartament) {
        this.nomDepartament = nomDepartament;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }
}
