package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.service.portfolio.PortfolioService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    private PortfolioService portfolioService;

    
    public PortfolioController(PortfolioService portfolioService){
        this.portfolioService = portfolioService;
    }
    
    //Alle Infos und Aktien aufgezählt
    @GetMapping("/get")
    public String getAlleAktienFuerPortfolio(@RequestParam String param) {
        return portfolioService.alleAktienFuerPortfolio();
    }

    //Alle Zusammengefasste Werte
    @GetMapping("/gesamt")
    public String getGesammtenWert(@RequestParam String param) {
        return portfolioService.gesammtWert();
    }
    
    //Summe aus allen Gesamtwerten
    @GetMapping("/summe")
    public String getSumme(@RequestParam String param) {
        return portfolioService.summe();
    }
    
    //Summe aus jeder Rendite
    @GetMapping("/rendite")
    public String getRendite(@RequestParam String param) {
        return portfolioService.rendite();
    }

    //Summe aus jeder Performance
    @GetMapping("/performance")
    public String getPerformance(@RequestParam String param) {
        return portfolioService.perfomance();
    }
    
    //Anteile der jeweiligen aktien in Prozent für die analyse
    /*@GetMapping("/analyse/${ticker}/anteile")
    public String getAnteileDerAktieInProzent(@PathVariable String ticker) {
        return portfolioService.anteileAktienInProzent(ticker);
    }
    
    @GetMapping("/analyse/${ticker}/")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
    */
}
