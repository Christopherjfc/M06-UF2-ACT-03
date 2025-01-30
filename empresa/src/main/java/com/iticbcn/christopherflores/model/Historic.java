package com.iticbcn.christopherflores.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Historic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Tasca;
    @Column
    private String dataInici;
    @Column
    private String dataFi;

    
}
