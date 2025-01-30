package com.iticbcn.christopherflores.model;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name="Empresa", uniqueConstraints = {@UniqueConstraint(columnNames={"nomEmpresa", "cif"})})
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpresa;
    @Column
    private String nomEmpresa;
    @Column
    private String cif;
    @Column
    private String direccion;

    // EMPRESA -> TIENE MUCHoS -> DEPARTAMENTOS 
    @OneToMany (mappedBy = "empresa", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<Departament> departaments;

    public Empresa(String nomEmpresa, String cif, String direccion) {
        this.nomEmpresa = nomEmpresa;
        this.cif = cif;
        this.direccion = direccion;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }
    
    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }
    
    public String getNomEmpresa() {
        return nomEmpresa;
    }
    
    public void setNomEmpresa(String nomEmpresa) {
        this.nomEmpresa = nomEmpresa;
    }
    
    public String getCif() {
        return cif;
    }
    
    public void setCif(String cif) {
        this.cif = cif;
    }
    
    public String getDireccion() {
        return direccion;
    }
    
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
