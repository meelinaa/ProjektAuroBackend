package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.service.portfolio.PortfolioService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService){
        this.portfolioService = portfolioService;
    }

    //Komplette Portfolio Infos nach ID
    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> getPortfolio(@PathVariable String id) {
        try{
            Integer portfolioId = Integer.parseInt(id);
            Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);

            if(portfolio != null){
                return ResponseEntity.ok(portfolio);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
    //Alle Aktien aufgezählt aus der ID
    @GetMapping("/aktien/get/{id}")
    public ResponseEntity<List<Aktie>> getAlleAktienFuerPortfolio(@PathVariable Integer id) {
        List<Aktie> allePortfolioAktien = portfolioService.getAllAktienById(id);

        if (allePortfolioAktien.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Optional: Entscheide, ob 404 für eine leere Liste sinnvoll ist.
        }

        return ResponseEntity.ok(allePortfolioAktien); // Rückgabe der Aktienliste mit 200 OK
    }

    @GetMapping("/order/get/{id}")
    public ResponseEntity<List<Order>>  getAlleOrderFuerPortfolio(@PathVariable Integer id) {
        List<Order> allePortfolioOrder = portfolioService.getAlleOrdersByID(id);

        if (allePortfolioOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Optional: Entscheide, ob 404 für eine leere Liste sinnvoll ist.
        }

        return ResponseEntity.ok(allePortfolioOrder); // Rückgabe der Aktienliste mit 200 OK
    }
    
    /*
    @GetMapping("/gesamtperformance/{id}")
    public ResponseEntity<Double> getGesamtPerformance(@PathVariable Integer id) {
        Double gesamtPerformance = portfolioService.getGesamtPerformance(id);

        if (gesamtPerformance != null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Optional: Entscheide, ob 404 für eine leere Liste sinnvoll ist.
        }

        return ResponseEntity.ok(gesamtPerformance);

    }
        */

   
    
    

    // -- TAKTISCH UNKLUG: LIEBER ON THE FLY LIVE BERECHNEN AUS DEN AKTIEN DIE IN DEM PORTFOLIO SIND; STATT DANN DIE SACHEN EXTRA TAUSENDFACH ABZUSPEUICHERN UND ZU BERECHNEN

    /*
    //Alle Zusammengefasste Werte
    @GetMapping("/gesamtwert/{id}")
    public ResponseEntity<Double> getGesammtenWert(@PathVariable Integer id) {
        Double gesamtWert = portfolioService.getGesamtWertById(id);

        if( gesamtWert != null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(gesamtWert);
    }
    
    //Summe aus jeder Rendite
    @GetMapping("/rendite/{id}")
    public ResponseEntity<Double> getRendite(@PathVariable String id) {
        try{
            Integer portfolioId = Integer.parseInt(id);
            Double rendite = portfolioService.rendite(portfolioId);

            if(rendite != null){
                return ResponseEntity.ok(rendite);
            }else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    
    //Summe aus jeder Performance
    @GetMapping("/performance/{id}")
    public ResponseEntity<Double> getPerformance(@PathVariable String id) {
        try {
            Integer portfolioId = Integer.parseInt(id);
            Double performance = portfolioService.performance(portfolioId);
        
            if (performance != null) {
                return ResponseEntity.ok(performance);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }   
        */
}