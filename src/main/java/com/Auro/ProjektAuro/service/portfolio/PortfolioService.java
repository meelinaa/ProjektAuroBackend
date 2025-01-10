package com.Auro.ProjektAuro.service.portfolio;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.repository.AktieRepository;
import com.Auro.ProjektAuro.repository.KontoRepository;
import com.Auro.ProjektAuro.repository.PortfolioRepository;

@Service
public class PortfolioService {

    @Autowired
    public PortfolioRepository portfolioRepository;

    @Autowired
    public KontoRepository kontoRepository;

    @Autowired
    public AktieRepository aktieRepository;

    public Portfolio getPortfolioById(Integer id) {
        return portfolioRepository.findById(id).orElse(null);
    }

    public List<Aktie> getAllAktienById(Integer portfolioId) {
        List<Aktie> alleAktien = portfolioRepository.findAllAktienById(portfolioId);

        return alleAktien;
    }
    
    public List<Order> getAlleOrdersByID(Integer portfolioId) {
        List<Order> alleOrder = portfolioRepository.findAllOrdersById(portfolioId);

        return alleOrder;
    }
   
}
