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

import lombok.Data;

@Service
@Data
@Scope("prototype")
public class OrderService {

    private Order order;
    private Konto konto;
    public Aktie aktie;
    private Portfolio portfolio;

    public Double kontoGuthaben;
    public Double neueAnzahlAnteile; 
    public Double aktuelleInvestition;
    public Double neueInvestition;
    public Double gesamtInvesition;
    public Double neuerBuyInKurs;

    private AktieRepository aktieRepository;
    private OrderRepository orderRepository;
    private PortfolioRepository portfolioRepository;
    private KontoRepository kontoRepository;

    public Boolean istAktieNeuErstellt;

    public OrderService( AktieRepository aktieRepository, OrderRepository orderRepository, PortfolioRepository portfolioRepository, KontoRepository kontoRepository){
        this.aktieRepository = aktieRepository;
        this.orderRepository = orderRepository;
        this.portfolioRepository = portfolioRepository;
        this.kontoRepository = kontoRepository;
    }

    // Main
    public void transaktion(OrderDto orderDto) {
        setAktie(null);
        setNeueAnzahlAnteile(null);
        setAktuelleInvestition(null);
        setNeueInvestition(null);
        setGesamtInvesition(null);
        setNeuerBuyInKurs(null);

        initialisiereObjekte(orderDto); 
        
        if ("buy".equals(orderDto.getOrderType())) {
            transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);
        } 
        else if("sell".equals(orderDto.getOrderType())){
            transaktionSell(orderDto, aktie, kontoGuthaben);
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
            || orderDto.getLiveKurs() == null 
            || orderDto.getAnteile() == null
            || orderDto.getCompanyName() == null) {
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

        istAktieNeuErstellt = false;
        aktie = aktieRepository.findById(orderDto.getTicker())
            .orElseGet(() -> {
                Aktie newAktie = new Aktie();
                newAktie.setId(orderDto.getTicker());
                newAktie.setBuyInKurs(orderDto.getLiveKurs());
                newAktie.setAnzahlAktienAnteile(orderDto.getAnteile());
                newAktie.setName(orderDto.getCompanyName());
                newAktie.setPortfolio(portfolio);
                istAktieNeuErstellt = true;
                return aktieRepository.save(newAktie);
            }); 
    }

    public void transaktionBuy(OrderDto orderDto, Aktie aktie, Boolean istAktieNeuErstellt, Double kontoGuthaben){
        if (kontoGuthaben == null) {
            throw new IllegalArgumentException("KontoGuthaben darf nicht leer sein");   
        }
        try {
            if(!istAktieNeuErstellt){
                neueAnzahlAnteile = aktie.getAnzahlAktienAnteile() + orderDto.getAnteile();
                aktuelleInvestition = aktie.getBuyInKurs() * aktie.getAnzahlAktienAnteile();
                neueInvestition = orderDto.getAnteile() * orderDto.getLiveKurs();
                gesamtInvesition = aktuelleInvestition + neueInvestition;
                neuerBuyInKurs = gesamtInvesition / neueAnzahlAnteile;
            }else{
                neueAnzahlAnteile = orderDto.getAnteile();
                neueInvestition = orderDto.getAnteile() * orderDto.getLiveKurs();
                gesamtInvesition =  neueInvestition;
                neuerBuyInKurs = gesamtInvesition / neueAnzahlAnteile;
            }
    
            //Konto: Guthaben abziehen
            kontoGuthaben -= neueInvestition;
            setKontoGuthaben(kontoGuthaben);
            setAktienAttributeUndSaveRepository(neuerBuyInKurs, neueAnzahlAnteile, aktie);
        } catch (Exception e) {
            throw new IllegalArgumentException("Es ist ein Fehler aufgetreten");
        }
        
        
    }

    public void transaktionSell(OrderDto orderDto, Aktie aktie, Double kontoGuthaben){
        
        if (aktie.getAnzahlAktienAnteile() == null 
            || aktie.getAnzahlAktienAnteile() <= 0 
            || aktie.getBuyInKurs() == null 
            || aktie.getBuyInKurs() <= 0) {
            throw new IllegalArgumentException("Es kann keine leere Aktie verkauft werden");
        }
        try {
            neueAnzahlAnteile = aktie.getAnzahlAktienAnteile() - orderDto.getAnteile();
            neuerBuyInKurs = aktie.getBuyInKurs();

            setNeueAnzahlAnteile(neueAnzahlAnteile);
            setNeuerBuyInKurs(neuerBuyInKurs);

            //Konto: Guthaben hinzufügen
            double verkauf = orderDto.getAnteile() * orderDto.getLiveKurs();
            kontoGuthaben += verkauf;
            setKontoGuthaben(kontoGuthaben);

            if (neueAnzahlAnteile == 0) {
                aktieRepository.delete(aktie);
                System.out.println("Aktie gelöscht: " + aktie.getId());
                return; 
            }

            setAktienAttributeUndSaveRepository(neuerBuyInKurs, neueAnzahlAnteile, aktie);
        }  catch (Exception e) {
            throw new IllegalArgumentException("Mindestens ein Wert in OrderDto ist leer.");
        }
        
    }

    public void setAktienAttributeUndSaveRepository(Double neuerBuyInKurs, Double neueAnzahlAnteile, Aktie aktie){
        aktie.setBuyInKurs(neuerBuyInKurs);
        aktie.setAnzahlAktienAnteile(neueAnzahlAnteile);
        
        aktieRepository.save(aktie);
    }

    public LocalDateTime getCurrentDateTime() {
        return LocalDateTime.now();
    }

    public void setzeUndSpeichereObjekte(OrderDto orderDto){
        order.setOrderDateAndTime(getCurrentDateTime());
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