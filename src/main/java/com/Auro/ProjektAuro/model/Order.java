package com.Auro.ProjektAuro.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="aktien_id", nullable = false)
    private Aktie aktie;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "portfolio_id") 
    private Portfolio portfolio;

    private LocalDateTime orderDateAndTime;

    private String orderType;

    private Double aktie_anteile;

    private Double buySellKurs;
}
