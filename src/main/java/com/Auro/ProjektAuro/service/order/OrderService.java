package com.Auro.ProjektAuro.service.order;

import java.time.LocalDateTime;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Konto;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.OrderDto;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.repository.AktieRepository;
import com.Auro.ProjektAuro.repository.KontoRepository;
import com.Auro.ProjektAuro.repository.OrderRepository;
import com.Auro.ProjektAuro.repository.PortfolioRepository;

@Service
public class OrderService {

    private AktieRepository aktieRepository;

    private OrderRepository orderRepository;

    private PortfolioRepository portfolioRepository;

    private KontoRepository kontoRepository;

    public OrderService( AktieRepository aktieRepository, OrderRepository orderRepository, PortfolioRepository portfolioRepository, KontoRepository kontoRepository){
        this.aktieRepository = aktieRepository;
        this.orderRepository = orderRepository;
        this.portfolioRepository = portfolioRepository;
        this.kontoRepository = kontoRepository;
    }

    public void transaktion(OrderDto orderDto) {
        Order order = new Order();

        Konto konto = kontoRepository.findById(1)
            .orElseThrow(() -> new RuntimeException("Konto konnte nicht gefunden werden"));
        
        double kontoGuthaben = kontoRepository.getGuthaben(1);

        Portfolio portfolio = portfolioRepository.findById(1)
            .orElseThrow(() -> new RuntimeException("Portfolio mit ID 1 nicht gefunden"));

        Aktie aktie = aktieRepository.findById(orderDto.getTicker())
            .orElseGet(() -> {
                Aktie newAktie = new Aktie();
                newAktie.setId(orderDto.getTicker());
                newAktie.setBuyInKurs(orderDto.getLiveKurs());
                newAktie.setAnzahlAktienAnteile(orderDto.getAnteile());
                newAktie.setName(orderDto.getCompanyName());
                newAktie.setPortfolio(portfolio);
                return aktieRepository.save(newAktie);
            });
    
        double neueAnzahlAnteile; 
        double aktuelleInvestition;
        double neueInvestition;
        double gesamtInvesition;
        double neuerBuyInKurs;

        if ("buy".equals(orderDto.getOrderType())) {
            neueAnzahlAnteile = aktie.getAnzahlAktienAnteile() + orderDto.getAnteile();
            aktuelleInvestition = aktie.getBuyInKurs() * aktie.getAnzahlAktienAnteile();
            neueInvestition = orderDto.getAnteile() * orderDto.getLiveKurs();
            gesamtInvesition = aktuelleInvestition + neueInvestition;
            neuerBuyInKurs = gesamtInvesition / neueAnzahlAnteile;

            //Konto: Guthaben abziehen
            kontoGuthaben -= neueInvestition;

            aktie.setBuyInKurs(neuerBuyInKurs);
            aktie.setAnzahlAktienAnteile(neueAnzahlAnteile);
            aktieRepository.save(aktie);
            
        } else if("sell".equals(orderDto.getOrderType())){
            neueAnzahlAnteile = aktie.getAnzahlAktienAnteile() - orderDto.getAnteile();
            neuerBuyInKurs = aktie.getBuyInKurs();

            //Konto: Guthaben hinzufügen
            double verkauf = orderDto.getAnteile() * orderDto.getLiveKurs();
            kontoGuthaben += verkauf;

            if (neueAnzahlAnteile == 0) {
                aktieRepository.delete(aktie);
                System.out.println("Aktie gelöscht: " + aktie.getId());
                return; 
            }

            aktie.setBuyInKurs(neuerBuyInKurs);
            aktie.setAnzahlAktienAnteile(neueAnzahlAnteile);
            aktieRepository.save(aktie);
        } else {
            throw new RuntimeException("Ungültiger OrderType: " + orderDto.getOrderType());
        }

        order.setOrderDateAndTime(LocalDateTime.now());
        order.setOrderType(orderDto.getOrderType());
        order.setAktie_anteile(orderDto.getAnteile());
        order.setBuySellKurs(orderDto.getLiveKurs());
        order.setPortfolio(portfolio);
        order.setAktienName(aktie.getName());
        order.setAktienTicker(aktie.getId());
    
        orderRepository.save(order);

        konto.setAktuellesKontoGuthaben(kontoGuthaben);
        kontoRepository.save(konto);

    }    

    public List<Order> getTransaktionen(){
        return orderRepository.findAll();
    }
}

