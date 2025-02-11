package com.iticbcn.christopherflores.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Departament implements Serializable{

    private int idDepartament;
    private String nomDepartament;
    private Set<Empleat> empleats = new HashSet<>();

    // constructor especifico
    public Departament(){}
    
    // constructor parametrizado
    public Departament(String nomDepartament) {
        this.nomDepartament = nomDepartament;
    }

    public void addEmpleat(Empleat empleat){
        empleats.add(empleat);
    }
    
    public Set<Empleat> getEmpleats() {
        return empleats;
    }

    public void setEmpleats(Set<Empleat> empleats) {
        this.empleats = empleats;
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

    @Override
    public String toString() {
        return "ID=" + idDepartament + ", Nombre=" + nomDepartament;
    }

    
}