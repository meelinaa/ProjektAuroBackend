package com.Auro.ProjektAuro.model;


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

    @Column(nullable = false, unique = true)
    private String isin;

    private Double aktuellerKurs;

    private Double buyInKurs;

    private Double anzahlAktienAnteile;

    private Double renditeProzent;

    private Double renditeGeldWert;

    private Double gesamtPerformance;

    private Double aktuellerGesamtWert;

    @ManyToOne
    @JoinColumn(name = "portfolio_id", nullable = false)
    private Portfolio portfolio;
}
