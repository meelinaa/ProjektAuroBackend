package com.Auro.ProjektAuro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Konto;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.service.portfolio.PortfolioService;

public class PortfolioControllerTest {

    PortfolioService portfolioService;
    PortfolioController portfolioController;

    Portfolio portfolio;
    Integer id = 1;

    @BeforeEach
    void setUp(){
        portfolioService = mock(PortfolioService.class);
        portfolioController = new PortfolioController(portfolioService);
    }

    public static Portfolio erstellePortfolioTestObjekt() {
        Integer id = 1;

        List<Aktie> aktien = List.of(
            new Aktie("AAPL", "Apple Inc.", null, 150.75, 10.0),
            new Aktie("TSLA", "Tesla Inc.", null, 750.50, 5.0)
        );

        List<Order> orders = List.of(
            new Order(1, null, LocalDateTime.of(2025, Month.JANUARY, 15, 10, 30), "AAPL", "Apple Inc.", "buy", 10.0, 150.75),
            new Order(2, null, LocalDateTime.of(2024, Month.DECEMBER, 25, 15, 45), "TSLA", "Tesla Inc.", "sell", 5.0, 750.50)
        );

        Konto konto = new Konto(1, null, "Testkonto", 10000.0);

        return new Portfolio(id, aktien, orders, konto);
    }

    @Nested
    @DisplayName("Tests für die Methode getPortfolio()")
    public class GetPortfolioTest {
        
        @Test
        void getPortfolioTestMitKorrekterAusführung(){
            when(portfolioService.getPortfolioById(id)).thenReturn(erstellePortfolioTestObjekt());

            ResponseEntity<Portfolio> response = portfolioController.getPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(erstellePortfolioTestObjekt(), response.getBody()); 
        }

        @Test
        void getPortfolioMitFehlerausgabeBeiLeererId(){
            id = null;

            ResponseEntity<Portfolio> response = portfolioController.getPortfolio(id);
            assertNotNull(response);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(portfolioService, never()).getPortfolioById(id);
            assertNull(response.getBody());
        }

        @Test
        void getPortfolioMitFehlerausgabeBeiLeeremPortfolio(){
            when(portfolioService.getPortfolioById(id)).thenReturn(null);

            ResponseEntity<Portfolio> response = portfolioController.getPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void getPortfolioMitFehlerausgabeBeiResponseEntityNichtOK(){
            when(portfolioService.getPortfolioById(id)).thenThrow(new RuntimeException());

            ResponseEntity<?> response = portfolioController.getPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
        
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleAktienFuerPortfolio()")
    public class GetAlleAktienFuerPortfolioTest {
        
        @Test
        void getAlleAktienFuerPortfolioTestMitKorrekterAusgabe(){
            List<Aktie> aktien = List.of(
                new Aktie("AAPL", "Apple Inc.", null, 150.75, 10.0),
                new Aktie("TSLA", "Tesla Inc.", null, 750.50, 5.0)
            );

            when(portfolioService.getAllAktienById(id)).thenReturn(aktien);

            ResponseEntity<List<Aktie>> response = portfolioController.getAlleAktienFuerPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(aktien, response.getBody()); 
        }

        @Test
        void getAlleAktienFuerPortfolioMitFehlerausgabeBeiLeererId(){
            id = null;

            ResponseEntity<List<Aktie>> response = portfolioController.getAlleAktienFuerPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(portfolioService, never()).getPortfolioById(id);
            assertNull(response.getBody());
        }

        @Test
        void getAlleAktienFuerPortfolioMitFehlerausgabeBeiLeeremPortfolio(){
            when(portfolioService.getAllAktienById(id)).thenReturn(null);

            ResponseEntity<List<Aktie>> response = portfolioController.getAlleAktienFuerPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void getAlleAktienFuerPortfolioMitFehlerausgabeBeiResponseEntityNichtOK(){
            when(portfolioService.getAllAktienById(id)).thenThrow(new RuntimeException());

            ResponseEntity<?> response = portfolioController.getAlleAktienFuerPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
    
    }

    @Nested
    @DisplayName("Tests für die Methode getAlleOrderFuerPortfolio()")
    public class GetAlleOrderFuerPortfolioTest {
        
        @Test
        void getAlleOrderFuerPortfolioMitKorrekterAusgabe(){
            List<Order> orders = List.of(
                new Order(1, null, LocalDateTime.of(2025, Month.JANUARY, 15, 10, 30), "AAPL", "Apple Inc.", "buy", 10.0, 150.75),
                new Order(2, null, LocalDateTime.of(2024, Month.DECEMBER, 25, 15, 45), "TSLA", "Tesla Inc.", "sell", 5.0, 750.50)
            );

            when(portfolioService.getAlleOrdersByID(id)).thenReturn(orders);

            ResponseEntity<List<Order>> response = portfolioController.getAlleOrderFuerPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(orders, response.getBody()); 
        }

        @Test
        void getAlleOrderFuerPortfolioMitFehlerausgabeBeiLeererId(){
            id = null;

            ResponseEntity<List<Order>> response = portfolioController.getAlleOrderFuerPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            verify(portfolioService, never()).getPortfolioById(id);
            assertNull(response.getBody());
        }

        @Test
        void getAlleOrderFuerPortfolioMitFehlerausgabeBeiLeeremPortfolio(){
            when(portfolioService.getAlleOrdersByID(id)).thenReturn(null);

            ResponseEntity<List<Order>> response = portfolioController.getAlleOrderFuerPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void getAlleOrderFuerPortfolioMitFehlerausgabeBeiResponseEntityNichtOK(){
            when(portfolioService.getAlleOrdersByID(id)).thenThrow(new RuntimeException());

            ResponseEntity<?> response = portfolioController.getAlleOrderFuerPortfolio(id);

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
        
    }
    
}
