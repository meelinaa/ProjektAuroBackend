package com.Auro.ProjektAuro.service.order;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.context.annotation.Scope;
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
@Scope("prototype")
public class OrderService {

    private Order order;
    private Konto konto;
    private Aktie aktie;
    private Portfolio portfolio;

    private Double kontoGuthaben;
    private Double neueAnzahlAnteile; 
    private Double aktuelleInvestition;
    private Double neueInvestition;
    private Double gesamtInvesition;
    private Double neuerBuyInKurs;

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

    // Main
    public void transaktion(OrderDto orderDto) {
        initialisiereObjekte(orderDto); 
        
        if ("buy".equals(orderDto.getOrderType())) {
            transaktionBuy(orderDto);
        } 
        else if("sell".equals(orderDto.getOrderType())){
            transaktionSell(orderDto);
        } else {
            throw new RuntimeException("Ungültiger OrderType: " + orderDto.getOrderType());
        }

        setzeUndSpeichereObjekte(orderDto);
    }   

    // Get
    public List<Order> getTransaktionen() {
        try {
            return orderRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Abrufen der Transaktionen im OrderRepository.", e);
        }
    }
    

    // Hilfsmethoden
    public void initialisiereObjekte(OrderDto orderDto){
        if (orderDto == null 
            || orderDto.getTicker() == null 
            || orderDto.getTicker().isEmpty() 
            || orderDto.getLiveKurs() == null 
            || orderDto.getAnteile() == null 
            || orderDto.getAnteile() <= 0 
            || orderDto.getCompanyName() == null 
            || orderDto.getCompanyName().isEmpty()) {
        throw new IllegalArgumentException("OrderDto ist ungültig oder unvollständig.");
        }
        order = new Order();

        Integer id = 1;

        konto = kontoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Konto konnte nicht gefunden werden"));
        
        kontoGuthaben = kontoRepository.getGuthaben(id);
        if (kontoGuthaben == null) {
            throw new RuntimeException("Guthaben konnte nicht gefunden werden");
        }
        portfolio = portfolioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Portfolio mit ID 1 nicht gefunden"));

        aktie = aktieRepository.findById(orderDto.getTicker())
            .orElseGet(() -> {
                Aktie newAktie = new Aktie();
                newAktie.setId(orderDto.getTicker());
                newAktie.setBuyInKurs(orderDto.getLiveKurs());
                newAktie.setAnzahlAktienAnteile(orderDto.getAnteile());
                newAktie.setName(orderDto.getCompanyName());
                newAktie.setPortfolio(portfolio);
                return aktieRepository.save(newAktie);
            }); 
    }

    public void transaktionBuy(OrderDto orderDto){
        neueAnzahlAnteile = aktie.getAnzahlAktienAnteile() + orderDto.getAnteile();
        aktuelleInvestition = aktie.getBuyInKurs() * aktie.getAnzahlAktienAnteile();
        neueInvestition = orderDto.getAnteile() * orderDto.getLiveKurs();
        gesamtInvesition = aktuelleInvestition + neueInvestition;
        neuerBuyInKurs = gesamtInvesition / neueAnzahlAnteile;

        //Konto: Guthaben abziehen
        kontoGuthaben -= neueInvestition;

        setAktienAttributeUndSaveRepository(neuerBuyInKurs, neueAnzahlAnteile, aktie);
    }

    public void transaktionSell(OrderDto orderDto){
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

        setAktienAttributeUndSaveRepository(neuerBuyInKurs, neueAnzahlAnteile, aktie);
    }

    public void setAktienAttributeUndSaveRepository(Double neuerBuyInKurs, Double neueAnzahlAnteile, Aktie aktie){
        aktie.setBuyInKurs(neuerBuyInKurs);
        aktie.setAnzahlAktienAnteile(neueAnzahlAnteile);
        
        aktieRepository.save(aktie);
    }

    public void setzeUndSpeichereObjekte(OrderDto orderDto){
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
    
}