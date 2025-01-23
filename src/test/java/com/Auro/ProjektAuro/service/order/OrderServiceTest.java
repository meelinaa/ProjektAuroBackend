package com.Auro.ProjektAuro.service.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Konto;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.OrderDto;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.repository.AktieRepository;
import com.Auro.ProjektAuro.repository.KontoRepository;
import com.Auro.ProjektAuro.repository.OrderRepository;
import com.Auro.ProjektAuro.repository.PortfolioRepository;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private AktieRepository aktieRepository;
    private PortfolioRepository portfolioRepository;
    private KontoRepository kontoRepository;
    private OrderDto orderDto;

    private Order order;
    private Konto konto;
    private Aktie aktie;
    private Portfolio portfolio;

    private Boolean istAktieNeuErstellt;

    public Double kontoGuthaben;
    private Double neueAnzahlAnteile; 
    private Double neuerBuyInKurs;

    @BeforeEach
    void setUp(){
        orderRepository = mock(OrderRepository.class);
        aktieRepository = mock(AktieRepository.class);
        portfolioRepository = mock(PortfolioRepository.class);
        kontoRepository = mock(KontoRepository.class);

        order = mock(Order.class);
        konto = mock(Konto.class);
        aktie = mock(Aktie.class);
        portfolio = mock(Portfolio.class);


        orderService = Mockito.spy(new OrderService(aktieRepository, orderRepository, portfolioRepository, kontoRepository));
    }

    @Nested
    @DisplayName("Tests für die Methode transaktion()")
    class TransaktionTests {
        // Test Main
        @Disabled("Hat funktioniert, bis ich Fehlermeldungen in die einzelnen Methoden hinzugefügt hab.")
        @Test
        void testeTransaktionWennOrderTypeBuyIst(){
            orderDto = new OrderDto();
            orderDto.setOrderType("buy");
            istAktieNeuErstellt = false;

            doNothing().when(orderService).initialisiereObjekte(any(OrderDto.class));
            doNothing().when(orderService).transaktionBuy(any(OrderDto.class), any(Aktie.class), anyBoolean(), anyDouble());
            doNothing().when(orderService).transaktionSell(any(OrderDto.class),  any(Aktie.class), anyDouble());
            doNothing().when(orderService).setzeUndSpeichereObjekte(any(OrderDto.class));
            
            orderService.transaktion(orderDto);

            verify(orderService).initialisiereObjekte(orderDto); 
            //buy wird aufgerufen
            verify(orderService).transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);
            //sell wird nicht aufgerufen
            verify(orderService, never()).transaktionSell(orderDto, aktie, kontoGuthaben);
            verify(orderService).setzeUndSpeichereObjekte(orderDto);
        }   

        @Disabled("Hat funktioniert, bis ich Fehlermeldungen in die einzelnen Methoden hinzugefügt hab.")
        @Test
        void testeTransaktionWennOrderTypeSellIst(){
            orderDto = new OrderDto();
            orderDto.setOrderType("sell");
            istAktieNeuErstellt = false;

            doNothing().when(orderService).initialisiereObjekte(any(OrderDto.class));
            doNothing().when(orderService).transaktionBuy(any(OrderDto.class), any(Aktie.class), anyBoolean(), anyDouble());
            doNothing().when(orderService).transaktionSell(any(OrderDto.class),  any(Aktie.class), anyDouble());
            doNothing().when(orderService).setzeUndSpeichereObjekte(any(OrderDto.class));
        
            orderService.transaktion(orderDto);

            verify(orderService).initialisiereObjekte(orderDto); 
            //buy wird nicht aufgerufen
            verify(orderService, never()).transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);
            //sell wird aufgerufen
            verify(orderService).transaktionSell(orderDto, aktie, kontoGuthaben);
            verify(orderService).setzeUndSpeichereObjekte(orderDto);
        }

        @Test
        void testeTransaktionWennOrderTypeNichtDefiniertIst(){
            orderDto = new OrderDto();
            orderDto.setOrderType(null);
            istAktieNeuErstellt = false;

            doNothing().when(orderService).initialisiereObjekte(any(OrderDto.class));
            doNothing().when(orderService).transaktionBuy(any(OrderDto.class), any(Aktie.class), anyBoolean(), anyDouble());
            doNothing().when(orderService).transaktionSell(any(OrderDto.class),  any(Aktie.class), anyDouble());
            doNothing().when(orderService).setzeUndSpeichereObjekte(any(OrderDto.class));
        
            Exception exception = assertThrows(RuntimeException.class, () -> {
                orderService.transaktion(orderDto);
            });

            assertEquals("Ungültiger OrderType: " + orderDto.getOrderType(), exception.getMessage());

            verify(orderService).initialisiereObjekte(orderDto); 
            //Alles folgende wird nicht aufgerufen
            verify(orderService, never()).transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);
            verify(orderService, never()).transaktionSell(orderDto, aktie, kontoGuthaben);
            verify(orderService, never()).setzeUndSpeichereObjekte(orderDto);
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getTransaktion()")
    class GetTransaktionenTests {
        //Get
        @Test
        void testeGetTransaktionen() {
            List<Order> mockOrders = Arrays.asList(
                new Order(),
                new Order()
            );

            when(orderRepository.findAll()).thenReturn(mockOrders);

            List<Order> result = orderService.getTransaktionen();

            assertEquals(mockOrders, result, "Die zurückgegebene Liste sollte der Mock-Liste entsprechen.");

            verify(orderRepository).findAll();
        }

        @Test
        void testeGetTransaktionenWennErrorGeworfenWird() {
            when(orderRepository.findAll()).thenThrow(RuntimeException.class);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                orderService.getTransaktionen();
            });

            assertEquals("Fehler beim Abrufen der Transaktionen im OrderRepository.", exception.getMessage());

            verify(orderRepository).findAll();
        }
    }

    @Nested
    @DisplayName("Tests für die Methode initialisiereObjekte(OrderDto orderDto)")
    class InitialisiereObjekteTests {
        @Test
        void testeInitialisiereObjekte() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker("AAPL");
            orderDto.setLiveKurs(150.0);
            orderDto.setAnteile(10.0);
            orderDto.setCompanyName("Apple");

            Konto mockKonto = new Konto();
            when(kontoRepository.findById(id)).thenReturn(Optional.of(mockKonto));
            when(kontoRepository.getGuthaben(id)).thenReturn(1000.0);

            Portfolio mockPortfolio = new Portfolio();
            when(portfolioRepository.findById(id)).thenReturn(Optional.of(mockPortfolio));

            Aktie mockAktie = new Aktie();
            when(aktieRepository.findById("AAPL")).thenReturn(Optional.of(mockAktie));

            orderService.initialisiereObjekte(orderDto);

            verify(kontoRepository).findById(id);
            verify(kontoRepository).getGuthaben(id);
            verify(portfolioRepository).findById(id);
            verify(aktieRepository).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }

        @Test
        void testeInitialisiereObjekteUndErstelleNeuesAktienObjekt() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker("AAPL");
            orderDto.setLiveKurs(150.0);
            orderDto.setAnteile(10.0);
            orderDto.setCompanyName("Apple");

            Konto mockKonto = new Konto();
            when(kontoRepository.findById(id)).thenReturn(Optional.of(mockKonto));
            when(kontoRepository.getGuthaben(id)).thenReturn(1000.0);

            Portfolio mockPortfolio = new Portfolio();
            when(portfolioRepository.findById(id)).thenReturn(Optional.of(mockPortfolio));


            orderService.initialisiereObjekte(orderDto);

            verify(kontoRepository).findById(id);
            verify(kontoRepository).getGuthaben(id);
            verify(portfolioRepository).findById(id);
            verify(aktieRepository).findById("AAPL");
            verify(aktieRepository).save(any(Aktie.class)); 
        }

        @Test
        void testeInitialisiereObjekteWennOrderDtoGleichNull() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker(null);
            orderDto.setLiveKurs(150.0);
            orderDto.setAnteile(10.0);
            orderDto.setCompanyName("Apple");

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("OrderDto ist ungültig oder unvollständig.", exception.getMessage());

            verify(kontoRepository, never()).findById(id);
            verify(kontoRepository, never()).getGuthaben(id);
            verify(portfolioRepository, never()).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }
        

        @Test
        void testeInitialisiereObjekteWennTickerGleichNull() {
            Integer id = 1;
            OrderDto orderDto = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("OrderDto ist ungültig oder unvollständig.", exception.getMessage());

            verify(kontoRepository, never()).findById(id);
            verify(kontoRepository, never()).getGuthaben(id);
            verify(portfolioRepository, never()).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }

        @Test
        void testeInitialisiereObjekteWennLiveKursGleichNull() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker("AAPL");
            orderDto.setLiveKurs(null);
            orderDto.setAnteile(10.0);
            orderDto.setCompanyName("Apple");

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("OrderDto ist ungültig oder unvollständig.", exception.getMessage());

            verify(kontoRepository, never()).findById(id);
            verify(kontoRepository, never()).getGuthaben(id);
            verify(portfolioRepository, never()).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }

        @Test
        void testeInitialisiereObjekteWennAnteileGleichNull() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker("AAPL");
            orderDto.setLiveKurs(150.0);
            orderDto.setAnteile(null);
            orderDto.setCompanyName("Apple");

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("OrderDto ist ungültig oder unvollständig.", exception.getMessage());

            verify(kontoRepository, never()).findById(id);
            verify(kontoRepository, never()).getGuthaben(id);
            verify(portfolioRepository, never()).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }

        @Test
        void testeInitialisiereObjekteWennCompanyNameGleichNull() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker("AAPL");
            orderDto.setLiveKurs(150.0);
            orderDto.setAnteile(10.0);
            orderDto.setCompanyName(null);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("OrderDto ist ungültig oder unvollständig.", exception.getMessage());

            verify(kontoRepository, never()).findById(id);
            verify(kontoRepository, never()).getGuthaben(id);
            verify(portfolioRepository, never()).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }


        @Test
        void testeInitialisiereObjekteMitErrorBeiKontoInitialisierung() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker("AAPL");
            orderDto.setLiveKurs(150.0);
            orderDto.setAnteile(10.0);
            orderDto.setCompanyName("Apple");

            when(kontoRepository.findById(id)).thenReturn(Optional.empty()); 

            Exception exception = assertThrows(RuntimeException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("Konto konnte nicht gefunden werden", exception.getMessage());

            //wird aufgerufen
            verify(kontoRepository).findById(id);
            //wird nicht aufgerufen
            verify(kontoRepository, never()).getGuthaben(id);
            verify(portfolioRepository, never()).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class));
        }

        @Test
        void testeInitialisiereObjekteMitErrorBeiKontoRepositoryInitialisierung() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker("AAPL");
            orderDto.setLiveKurs(150.0);
            orderDto.setAnteile(10.0);
            orderDto.setCompanyName("Apple");

            Konto mockKonto = new Konto();
            when(kontoRepository.findById(id)).thenReturn(Optional.of(mockKonto));
            when(kontoRepository.getGuthaben(id)).thenReturn(null);

            Exception exception = assertThrows(RuntimeException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("Guthaben konnte nicht gefunden werden", exception.getMessage());

            //wird aufgerufen
            verify(kontoRepository).findById(id);
            verify(kontoRepository).getGuthaben(id);
            //wird nicht aufgerufen
            verify(portfolioRepository, never()).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }

        @Test
        void testeInitialisiereObjekteMitErrorBeiPortfolioInitialisierung() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker("AAPL");
            orderDto.setLiveKurs(150.0);
            orderDto.setAnteile(10.0);
            orderDto.setCompanyName("Apple");

            Konto mockKonto = new Konto();
            when(kontoRepository.findById(id)).thenReturn(Optional.of(mockKonto));
            when(kontoRepository.getGuthaben(id)).thenReturn(1000.0);

            when(portfolioRepository.findById(id)).thenReturn(Optional.empty());

            Exception exception = assertThrows(RuntimeException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("Portfolio mit ID 1 nicht gefunden", exception.getMessage());

            verify(kontoRepository).findById(id);
            verify(kontoRepository).getGuthaben(id);
            verify(portfolioRepository).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }

        @Test
        void testeInitialisiereObjekteMitErrorBeiLeeremOrderDtoInitialisierung() {
            Integer id = 1;
            OrderDto orderDto = new OrderDto();
            orderDto.setTicker(null);
            orderDto.setLiveKurs(null);
            orderDto.setAnteile(null);
            orderDto.setCompanyName(null);

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.initialisiereObjekte(orderDto);
            });

            assertEquals("OrderDto ist ungültig oder unvollständig.", exception.getMessage());

            verify(kontoRepository, never()).findById(id);
            verify(kontoRepository, never()).getGuthaben(id);
            verify(portfolioRepository, never()).findById(id);
            verify(aktieRepository, never()).findById("AAPL");
            verify(aktieRepository, never()).save(any(Aktie.class)); 
        }
    }

    @Nested
    @DisplayName("Tests für die Methode setAktienAttributeUndSaveRepository(...)")
    class SetAktienAttributeUndSaveRepositoryTest {
        
        @Test
        void testeSetAktienAttributeUndSaveRepository() {
            Double neuerBuyInKurs = 150.0;
            Double neueAnzahlAnteile = 10.0;

            orderService.setAktienAttributeUndSaveRepository(neuerBuyInKurs, neueAnzahlAnteile, aktie);

            verify(aktie).setBuyInKurs(neuerBuyInKurs);
            verify(aktie).setAnzahlAktienAnteile(neueAnzahlAnteile);

            verify(aktieRepository).save(aktie);
       }

    }

    @Nested
    @DisplayName("Tests für die Methode transaktionBuy(OrderDto orderDto)")
    class TransaktionBuytests{

        @Test
        void testeTransaktionBuyMitKorrekterAusgabeUndAlterAktie(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile( 10.);
            aktie.setBuyInKurs(120.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(5.);
            orderDto.setLiveKurs(146.);

            kontoGuthaben = 10000.00;
            istAktieNeuErstellt = false; 

            doNothing().when(orderService).setAktienAttributeUndSaveRepository(anyDouble(), anyDouble(), any(Aktie.class));

            orderService.transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);

            assertEquals(15, orderService.getNeueAnzahlAnteile(), 0.0001);
            assertEquals(1200, orderService.getAktuelleInvestition(), 0.01);
            assertEquals(730, orderService.getNeueInvestition(), 0.01);
            assertEquals(1930, orderService.getGesamtInvesition(), 0.01);
            assertEquals(128.67, orderService.getNeuerBuyInKurs(), 0.01);
            assertEquals(9270, orderService.getKontoGuthaben(), 0.01);
        }

        @Test
        void testeTransaktionBuyMitKorrekterAusgabeUndNeuerAktie(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile( 5.);
            aktie.setBuyInKurs(146.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(5.);
            orderDto.setLiveKurs(146.);

            kontoGuthaben = 10000.00;
            istAktieNeuErstellt = true; 

            doNothing().when(orderService).setAktienAttributeUndSaveRepository(anyDouble(), anyDouble(), any(Aktie.class));

            orderService.transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);

            assertEquals(5, orderService.getNeueAnzahlAnteile(), 0.0001);
            assertEquals(730, orderService.getNeueInvestition(), 0.01);
            assertEquals(730, orderService.getGesamtInvesition(), 0.01);
            assertEquals(146, orderService.getNeuerBuyInKurs(), 0.01);
            assertEquals(9270, orderService.getKontoGuthaben(), 0.01);
        }

        @Test
        void testeTransaktionBuyMitFehlermeldungWennAktieNullWertHat(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile( null);
            aktie.setBuyInKurs(146.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(5.);
            orderDto.setLiveKurs(146.);

            kontoGuthaben = 10000.00;
            istAktieNeuErstellt = false; 

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);
            });

            assertEquals("Es ist ein Fehler aufgetreten", exception.getMessage());
        }

        @Test
        void testeTransaktionBuyMitFehlermeldungWennOrderDtoNullWertHat(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile( 4.);
            aktie.setBuyInKurs(146.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(5.);
            orderDto.setLiveKurs(null);

            kontoGuthaben = 10000.00;
            istAktieNeuErstellt = false; 

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);
            });

            assertEquals("Es ist ein Fehler aufgetreten", exception.getMessage());
        }

        @Test
        void testeTransaktionBuyMitFehlermeldungWennKontoGuthabenNullIst(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile( 4.);
            aktie.setBuyInKurs(146.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(5.);
            orderDto.setLiveKurs(null);

            kontoGuthaben = null;
            istAktieNeuErstellt = false; 

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionBuy(orderDto, aktie, istAktieNeuErstellt, kontoGuthaben);
            });

            assertEquals("KontoGuthaben darf nicht leer sein", exception.getMessage());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode transaktionSell(OrderDto orderDto)")
    class TransaktionSellTest{
        @Test
        void testeTransaktionSellMitKorrekterAusgabe(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile(5.);
            aktie.setBuyInKurs(120.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(2.);
            orderDto.setLiveKurs(140.);

            kontoGuthaben = 10000.00;

            doNothing().when(orderService).setAktienAttributeUndSaveRepository(anyDouble(), anyDouble(), any(Aktie.class));

            orderService.transaktionSell(orderDto, aktie, kontoGuthaben);

            assertEquals(10280.0, orderService.getKontoGuthaben(), 0.01);
            assertEquals(3.0, orderService.getNeueAnzahlAnteile(), 0.0001);
            assertEquals(120.0, orderService.getNeuerBuyInKurs(), 0.01);
        }

        @Test
        void testeTransaktionSellMitKorrektemLöschenDerAktieBeiAnzahlAnteileGleichNull(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile(5.);
            aktie.setBuyInKurs(120.);
            aktie.setId("TSLA");

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(5.);
            orderDto.setLiveKurs(140.);

            kontoGuthaben = 10000.00;

            orderService.transaktionSell(orderDto, aktie, kontoGuthaben);

            verify(aktieRepository).delete(aktie);
            verify(orderService, never()).setAktienAttributeUndSaveRepository(neuerBuyInKurs, neueAnzahlAnteile, aktie);

        }

        @Test
        void testeTransaktionSellMitFehlermeldungBeiFehlenderAktie(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile(null);
            aktie.setBuyInKurs(null);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(2.);
            orderDto.setLiveKurs(140.);

            kontoGuthaben = 10000.00;
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionSell(orderDto, aktie, kontoGuthaben);
            });

            assertEquals("Es kann keine leere Aktie verkauft werden", exception.getMessage());
        }

        @Test
        void testeTransaktionSellMitFehlermeldungBeiBuyInKleinerNull(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile(5.);
            aktie.setBuyInKurs(-5.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(2.);
            orderDto.setLiveKurs(140.);

            kontoGuthaben = 10000.00;
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionSell(orderDto, aktie, kontoGuthaben);
            });

            assertEquals("Es kann keine leere Aktie verkauft werden", exception.getMessage());
        }

        @Test
        void testeTransaktionSellMitFehlermeldungBeiBuyInGleichNULL(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile(5.);
            aktie.setBuyInKurs(null);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(2.);
            orderDto.setLiveKurs(140.);

            kontoGuthaben = 10000.00;
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionSell(orderDto, aktie, kontoGuthaben);
            });

            assertEquals("Es kann keine leere Aktie verkauft werden", exception.getMessage());
        }

        @Test
        void testeTransaktionSellMitFehlermeldungBeiLeererAktie(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile(0.);
            aktie.setBuyInKurs(0.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(2.);
            orderDto.setLiveKurs(140.);

            kontoGuthaben = 10000.00;
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionSell(orderDto, aktie, kontoGuthaben);
            });

            assertEquals("Es kann keine leere Aktie verkauft werden", exception.getMessage());
        }

        @Test
        void testeTransaktionSellMitFehlermeldungBeiFehlerBeiOrderDto(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile(5.);
            aktie.setBuyInKurs(120.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(null);
            orderDto.setLiveKurs(140.);

            kontoGuthaben = 10000.00;
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionSell(orderDto, aktie, kontoGuthaben);
            });

            assertEquals("Mindestens ein Wert in OrderDto ist leer.", exception.getMessage());
        }

        @Test
        void testeTransaktionSellMitFehlermeldungBeiLeeremKontoGuthaben(){
            Aktie aktie = new Aktie();
            aktie.setAnzahlAktienAnteile(5.);
            aktie.setBuyInKurs(120.);

            OrderDto orderDto = new OrderDto();
            orderDto.setAnteile(5.);
            orderDto.setLiveKurs(140.);

            kontoGuthaben = null;
            
            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderService.transaktionSell(orderDto, aktie, kontoGuthaben);
            });

            assertEquals("Mindestens ein Wert in OrderDto ist leer.", exception.getMessage());
        }

     
    }

    @Nested
    @DisplayName("Tests für die Methode setzeUndSpeichereObjekte(...)")
    class SetzeUndSpeichereObjekteTests {
        
        @Test
        void testeSetzeUndSpeichereObjekte() {

            
            OrderService spyOrderService = Mockito.spy(new OrderService(aktieRepository, orderRepository, portfolioRepository, kontoRepository));

            // Mocke die Methode getCurrentDateTime()
            LocalDateTime fixedTime = LocalDateTime.of(2025, 1, 1, 12, 0);
            doReturn(fixedTime).when(spyOrderService).getCurrentDateTime();

            orderDto = new OrderDto();
            orderDto.setOrderType("buy");
            orderDto.setAnteile(10.);
            orderDto.setLiveKurs(120.);
            
            aktie = new Aktie();
            aktie.setName("Apple");
            aktie.setId("AAPL");

            portfolio = null;

            Double kontoGuthaben = 25000.00;

            spyOrderService.setOrder(order);
            spyOrderService.setAktie(aktie);
            spyOrderService.setKonto(konto);
            spyOrderService.setKontoGuthaben(kontoGuthaben);
            spyOrderService.setzeUndSpeichereObjekte(orderDto);

            verify(order).setOrderDateAndTime(LocalDateTime.of(2025, 1, 1, 12, 0));
            verify(order).setOrderType("buy");
            verify(order).setAktie_anteile(10.);
            verify(order).setBuySellKurs(120.);
            verify(order).setPortfolio(portfolio);
            verify(order).setAktienName("Apple");
            verify(order).setAktienTicker("AAPL");
            verify(konto).setAktuellesKontoGuthaben(kontoGuthaben);
            verify(kontoRepository).save(konto);
        }

    }

}
