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

    // Ein einzelnes Portfolio ausgeben
    public Portfolio getPortfolioById(Integer id) {
        return portfolioRepository.findById(id).orElse(null);
    }

    // Alle Portfolios ausgeben
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    public List<Aktie> getAllAktienById(Integer portfolioId) {
        List<Aktie> alleAktien = portfolioRepository.findAllAktienById(portfolioId);

        if(alleAktien != null){
            return alleAktien;
        } else {
            return null;
        }
    }
    
    public List<Order> getAlleOrdersByID(Integer portfolioId) {
        List<Order> alleOrder = portfolioRepository.findAllOrdersById(portfolioId);

        if(alleOrder != null){
            return alleOrder;
        } else {
            return null;
        }
    }

    // -- bessere Logik einfügen zum Berechnen der Performance
    // muss so sein, dass ich über einen API zugriff dann über die ticker der aktien auf den aktuellen 
    // Kurs zugreifen kann und darüber mit der differenz die rendite berechnen kann von den investierten sachen. 
    // -- Erstmal Aktien sachen bestimmen, damit ich nur noch diese methoden aufrufen muss 
    /*
    public Double getGesamtPerformance(Integer portfolioId) {
        List<Aktie> alleAktien = getAllAktienById(portfolioId);

        Double gesamtPerformance = (double) 0;
        for (Aktie aktie : alleAktien) {
            gesamtPerformance += aktieRepository.getGesamtPerformance();
        }

        return null;
    }
    */

/*
    public Double rendite(Integer portfolioId){
        Double rendite = portfolioRepository.findRenditeById(portfolioId);

        if (rendite != null){
            return rendite;
        } else {
            return null;
        }
    }

    public Double performance(Integer portfolioId) {
        Double performance = portfolioRepository.findPerformanceById(portfolioId);

        if (performance != null) {
            return performance;
        } else {
            return null;
        }
    }


    

    public Double getGesamtWertById(Integer portfolioId) {
        Double gesamtWert = portfolioRepository.getGesamtWertPortfolio(portfolioId);
        if(gesamtWert != null){
            return gesamtWert;
        } else {
            return null;
        }
    }
*/
    

   
}
