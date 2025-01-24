package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.service.portfolio.PortfolioService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/portfolio")
@AllArgsConstructor
@NoArgsConstructor
public class PortfolioController {

    private PortfolioService portfolioService;

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
    
    @GetMapping("/aktien/get/{id}")
    public ResponseEntity<List<Aktie>> getAlleAktienFuerPortfolio(@PathVariable Integer id) {
        List<Aktie> allePortfolioAktien = portfolioService.getAllAktienById(id);

        return ResponseEntity.ok(allePortfolioAktien); 
    }

    @GetMapping("/order/get/{id}")
    public ResponseEntity<List<Order>>  getAlleOrderFuerPortfolio(@PathVariable Integer id) {
        List<Order> allePortfolioOrder = portfolioService.getAlleOrdersByID(id);

        if (allePortfolioOrder.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(allePortfolioOrder);
    }
    
}