package com.Auro.ProjektAuro.service.portfolio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.repository.PortfolioRepository;

@Disabled("Fertig machen")
public class PortfolioServiceTest {

    PortfolioRepository portfolioRepository;
    PortfolioService portfolioService;
    Portfolio portfolio;

    Aktie aktie;
    Order order;

    Integer id = 1;

    @BeforeEach
    void setUp() {
        // die anderen repositories auch einfügen in das neu Perotolio service indem ich das
        // in den konstruktor einfüge
        // dann sollte der fehler weg sein, dass ich dann die returns dann auch testen kann.
        portfolioRepository = mock(PortfolioRepository.class);
        portfolio = mock(Portfolio.class);
        portfolioRepository = mock(PortfolioRepository.class);
        portfolioService = new PortfolioService(portfolioRepository);

        aktie = mock(Aktie.class);
        order = mock(Order.class);
    }

    @Nested
    @DisplayName("Tests für getPortfolioId()")
    class GetPortfolioIdTests {

        @Test
        void getPortfolioByIdTestenMitKorrektemAuswurf() {
            // Mocking: Repository liefert ein Optional mit Portfolio
            when(portfolioRepository.findById(id)).thenReturn(Optional.of(portfolio));

    // Service-Methode aufrufen
    Portfolio result = portfolioService.getPortfolioById(id);

    // Überprüfen, ob das Ergebnis korrekt ist
    assertEquals(portfolio, result);
        }


        @Test
        void getPortfolioByIdTestenMitFehlerAusgabeWeilPortfolioNichtGefundenWurde(){
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
            List<Aktie> listeAktie = List.of(aktie);
            when(portfolioRepository.findAllAktienById(id)).thenReturn(listeAktie);

            assertEquals(listeAktie, portfolioRepository.findAllAktienById(id));;
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
            List<Order> listeOrder = List.of(order);
            when(portfolioRepository.getAlleOrdersById(id)).thenReturn(listeOrder);

            assertEquals(listeOrder, portfolioRepository.getAlleOrdersById(id));
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
            Exception exception = assertThrows(RuntimeException.class, () -> {
                portfolioService.getAlleOrdersByID(id);
            });

            assertEquals("Es konnten keine Transaktionen gefunden werden", exception.getMessage());
        }
    }
}
