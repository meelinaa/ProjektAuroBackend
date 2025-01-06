package com.Auro.ProjektAuro.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Aktie {
    
    @Id
    @Column(nullable = false, unique = true)
    private String id;

    private String name;

    private String isin;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "portfolio_id") 
    private Portfolio portfolio;

    private Double aktuellerKurs;

    private Double buyInKurs;

    private Double anzahlAktienAnteile;

    
    
}
