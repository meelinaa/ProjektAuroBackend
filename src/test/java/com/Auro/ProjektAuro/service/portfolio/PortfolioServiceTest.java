package com.Auro.ProjektAuro.service.portfolio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.repository.PortfolioRepository;

public class PortfolioServiceTest {

    PortfolioRepository portfolioRepository;
    PortfolioService portfolioService;
    Portfolio portfolio;

    Aktie aktie;
    Order order;

    Integer id = 1;

    @BeforeEach
    void setUp() {
        portfolioRepository = mock(PortfolioRepository.class);
        portfolio = mock(Portfolio.class);
        portfolioService = new PortfolioService(portfolioRepository);
    }

    @Nested
    @DisplayName("Tests für getPortfolioId()")
    class GetPortfolioIdTests {

        @Test
        void getPortfolioByIdTestenMitKorrektemAuswurf() {
            when(portfolioRepository.findById(id)).thenReturn(Optional.of(portfolio));

            Portfolio result = portfolioService.getPortfolioById(id);
                
            assertEquals(portfolio, result);
        }


        @Test
        void getPortfolioByIdTestenMitFehlerAusgabeWeilPortfolioNichtGefundenWurde() {        
            when(portfolioService.portfolioRepository.findById(anyInt())).thenThrow(new RuntimeException("Portfolio konnte nicht gefunden werden"));
                
            Exception exception = assertThrows(RuntimeException.class, () -> {
                portfolioService.getPortfolioById(id);
            });

            assertEquals("Portfolio konnte nicht gefunden werden", exception.getMessage());
        }


        @Test
        void getPortfolioByIdTestenMitFehlerAusgabeWeilIDLeerIst(){
            id = null;
                        
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                portfolioService.getPortfolioById(id);
            });

            assertEquals("Es wurde keine gültige ID weitergegeben.", exception.getMessage());
        }
    }
    
    @Nested
    @DisplayName("Test für getAlleAktienById()")
    class GetAlleAktienById {

        @Test
        void getAlleAktienByIdMitKorrekterAusgabe(){
            Aktie aktie1 = new Aktie();
            aktie1.setId("AAPL");
            aktie1.setName("Apple");
            aktie1.setAnzahlAktienAnteile(10.0);
            aktie1.setBuyInKurs(150.0);

            Aktie aktie2 = new Aktie();
            aktie2.setId("MSFT");
            aktie2.setName("Microsoft");
            aktie2.setAnzahlAktienAnteile(5.0);
            aktie2.setBuyInKurs(250.0);

            Aktie aktie3 = new Aktie();
            aktie3.setId("GOOGL");
            aktie3.setName("Google");
            aktie3.setAnzahlAktienAnteile(7.0);
            aktie3.setBuyInKurs(120.0);

            List<Aktie> listeAktie = List.of(aktie1, aktie2, aktie3);

            when(portfolioRepository.findAllAktienById(id)).thenReturn(listeAktie);

            assertEquals(listeAktie, portfolioService.getAllAktienById(id));;
        }

        @Test
        void getAlleAktienByIdMitFehlerAusgabeWennIdNullIst(){
            id = null;

            Exception exception = assertThrows(IllegalStateException.class, () -> {
                portfolioService.getAllAktienById(id);
            });

            assertEquals("Es wurde keine gültige ID weitergegeben.", exception.getMessage());
        }

        @Test
        void getAlleAktienByIdMitFehlerAusgabeWennAktienNichtGefundenWerden(){
            when(portfolioService.portfolioRepository.findAllAktienById(anyInt())).thenThrow(new RuntimeException("Es konnten keine Aktien gefunden werden"));
                
            Exception exception = assertThrows(RuntimeException.class, () -> {
                portfolioService.getAllAktienById(id);
            });

            assertEquals("Es konnten keine Aktien gefunden werden", exception.getMessage());
        }
    }
    

    @Nested
    @DisplayName("Test für getAlleOrdersByID()")
    class GetAlleOrdersByID {

        @Test
        void getAlleOrdersByIDMitKorrekterAusgabe(){
            Order order1 = new Order();
            order1.setId(1);
            order1.setAktienName("Apple");
            order1.setAktie_anteile(10.0);
            order1.setBuySellKurs(120.0);

            Order order2 = new Order();
            order2.setId(2);
            order2.setAktienName("Microsoft");
            order2.setAktie_anteile(5.0);          
            order2.setBuySellKurs(250.0);

            Order order3 = new Order();
            order3.setId(3);
            order3.setAktienName("Google");
            order3.setAktie_anteile(7.0);
            order3.setBuySellKurs(145.0);

            List<Order> listeOrder = List.of(order1, order2, order3);

            when(portfolioRepository.findAllOrdersById(id)).thenReturn(listeOrder);

            assertEquals(listeOrder, portfolioService.getAlleOrdersByID(id));
        }

        @Test
        void getAlleOrdersByIDMitFehlerAusgabeWennIdNullIst(){
            id = null;
            Exception exception = assertThrows(IllegalStateException.class, () -> {
                portfolioService.getAlleOrdersByID(id);
            });

            assertEquals("Es wurde keine gültige ID weitergegeben.", exception.getMessage());
        }

        @Test
        void getAlleOrdersByIDMitFehlerAusgabeWennOrderNichtGefundenWerden(){
            when(portfolioService.portfolioRepository.findAllOrdersById(anyInt())).thenThrow(new RuntimeException("Es konnten keine Transaktionen gefunden werden"));
            Exception exception = assertThrows(RuntimeException.class, () -> {
                portfolioService.getAlleOrdersByID(id);
            });

            assertEquals("Es konnten keine Transaktionen gefunden werden", exception.getMessage());
        }
    }
}
