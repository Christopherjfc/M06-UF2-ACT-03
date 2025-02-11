package com.iticbcn.christopherflores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Historic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idHistoric;
    @Column(nullable = false)
    private String dataInici;
    @Column(nullable = false)
    private String dataFi;
    
    @ManyToOne
    @JoinColumn(name = "idTasca", foreignKey = @ForeignKey(name = "FK_HIST_TASCA"), nullable = false)
    private Tasca tasca;

    public Historic() {}

    public Historic(String dataInici, String dataFi, Tasca tasca) {
        this.dataInici = dataInici;
        this.dataFi = dataFi;
        this.tasca = tasca;
    }

    public int getIdHistoric() {
        return idHistoric;
    }

    public void setIdHistoric(int idHistoric) {
        this.idHistoric = idHistoric;
    }

    public String getDataInici() {
        return dataInici;
    }

    public void setDataInici(String dataInici) {
        this.dataInici = dataInici;
    }

    public String getDataFi() {
        return dataFi;
    }

    public void setDataFi(String dataFi) {
        this.dataFi = dataFi;
    }

    public Tasca getTasca() {
        return tasca;
    }

    public void setTasca(Tasca tasca) {
        this.tasca = tasca;
    }

    @Override
    public String toString() {
        return "ID=" + idHistoric + ", Fecha_Inicio=" + dataInici + ", Fecha_Final=" + dataFi + ", id_Tasca="+ tasca.getIdTasca();
    }

    
}
