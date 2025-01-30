package com.iticbcn.christopherflores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Empleat", uniqueConstraints = { @UniqueConstraint(columnNames = { "dni", "correu", "telefon" }) })
public class Empleat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpleat;
    @Column
    private String nomEmpleat;
    @Column
    private String dni;
    @Column
    private String correu;
    @Column
    private String telefon;
    
    // MUCHOS EMPLEADOS -> TIENEN SOLO 1 -> DEPARTAMENTO 
    @ManyToOne
    @JoinColumn(name = "idDepartament", foreignKey = @ForeignKey(name = "FK_EMPL_DEPARTAMENT"), nullable = false)
    private Departament departament;

    public Empleat(String nomEmpleat, String dni, String correu, String telefon, Departament departament) {
        this.nomEmpleat = nomEmpleat;
        this.dni = dni;
        this.correu = correu;
        this.telefon = telefon;
        this.departament = departament;
    }

    public int getIdEmpleat() {
        return idEmpleat;
    }

    public void setIdEmpleat(int idEmpleat) {
        this.idEmpleat = idEmpleat;
    }

    public String getNomEmpleat() {
        return nomEmpleat;
    }

    public void setNomEmpleat(String nomEmpleat) {
        this.nomEmpleat = nomEmpleat;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getCorreu() {
        return correu;
    }

    public void setCorreu(String correu) {
        this.correu = correu;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

}
