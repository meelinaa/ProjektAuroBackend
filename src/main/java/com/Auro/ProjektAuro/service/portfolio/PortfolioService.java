package com.Auro.ProjektAuro.service.portfolio;

import java.util.List;

import org.springframework.stereotype.Service;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.repository.PortfolioRepository;

import lombok.Data;

@Service
@Data
public class PortfolioService {

    public PortfolioRepository portfolioRepository;

    public PortfolioService(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    public Portfolio getPortfolioById(Integer id) {
        if (id == null) {
            throw new IllegalStateException("Es wurde keine gültige ID weitergegeben.");
        }
        try {
            return portfolioRepository.findById(id).orElse(null);
        } catch (RuntimeException e) {
            throw new RuntimeException("Portfolio konnte nicht gefunden werden");
        }
    }

    public List<Aktie> getAllAktienById(Integer portfolioId) {
        if (portfolioId == null) {
            throw new IllegalStateException("Es wurde keine gültige ID weitergegeben.");
        }
        try {
            List<Aktie> alleAktien = portfolioRepository.findAllAktienById(portfolioId);
            return alleAktien;
        } catch (RuntimeException e) {
            throw new RuntimeException("Es konnten keine Aktien gefunden werden");
        }

    }
    
    public List<Order> getAlleOrdersByID(Integer portfolioId) {
        if (portfolioId == null) {
            throw new IllegalStateException("Es wurde keine gültige ID weitergegeben.");
        }
        try {
            List<Order> alleOrder = portfolioRepository.findAllOrdersById(portfolioId);
            return alleOrder;
        } catch (Exception e) {
            throw new RuntimeException("Es konnten keine Transaktionen gefunden werden");
        }
    }
   
}
