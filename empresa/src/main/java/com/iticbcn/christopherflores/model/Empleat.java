package com.iticbcn.christopherflores.model;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "Empleat", uniqueConstraints = { 
    @UniqueConstraint(columnNames = { "dni" }), 
    @UniqueConstraint(columnNames = { "correu" }),
    @UniqueConstraint(columnNames = { "telefon" }) 
})
public class Empleat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpleat;
    @Column(nullable = false)
    private String nomEmpleat;
    @Column(nullable = false)
    private String dni;
    @Column(nullable = false)
    private String correu;
    @Column(nullable = false)
    private String telefon;
    
    // MUCHOS EMPLEADOS -> TIENEN SOLO 1 -> DEPARTAMENTO 
    @ManyToOne
    @JoinColumn(name = "idDepartament", foreignKey = @ForeignKey(name = "FK_EMPL_DEPARTAMENT"), nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Departament departament;

    @ManyToMany
    @JoinTable(name = "Empleado_Tarea", 
    joinColumns = {@JoinColumn(name="idEmpleat",foreignKey = @ForeignKey(name="FK_EMPTAR_EMPLEAT"))},
    inverseJoinColumns = {@JoinColumn(name="idTasca", foreignKey = @ForeignKey(name="FK_EMPTAR_TASCA"))}) 
    private Set<Tasca> tasca = new HashSet<>();

    public Empleat () {}

    public Empleat(String nomEmpleat, String dni, String correu, String telefon, Departament departament) {
        this.nomEmpleat = nomEmpleat;
        this.dni = dni;
        this.correu = correu;
        this.telefon = telefon;
        this.departament = departament;
    }

    public void addTasca(Tasca tasca) {
        if (!this.tasca.contains(tasca)) {
            this.tasca.add(tasca);
        }
        tasca.getEmpleat().add(this);
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

    public Set<Tasca> getTasca() {
        return tasca;
    }

    public void setTasca(Set<Tasca> tasca) {
        this.tasca = tasca;
    }

    @Override
    public String toString() {
        return "ID=" + idEmpleat + ", Nombre=" + nomEmpleat + ", DNI=" + dni + ", Correo=" + correu
                + ", Teléfono=" + telefon + ", Departament=" + departament.getIdDepartament();
    }

    
}
