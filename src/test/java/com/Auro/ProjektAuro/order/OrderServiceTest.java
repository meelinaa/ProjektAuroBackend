package com.Auro.ProjektAuro.order;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
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
import com.Auro.ProjektAuro.service.order.OrderService;

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

    Boolean istAktieNeuErstellt;

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
        @Test
        void testeTransaktionWennOrderTypeBuyIst(){
            orderDto = new OrderDto();
            orderDto.setOrderType("buy");

            doNothing().when(orderService).initialisiereObjekte(any(OrderDto.class));
            doNothing().when(orderService).transaktionBuy(any(OrderDto.class));
            doNothing().when(orderService).transaktionSell(any(OrderDto.class));
            doNothing().when(orderService).setzeUndSpeichereObjekte(any(OrderDto.class));
            
            orderService.transaktion(orderDto);

            verify(orderService).initialisiereObjekte(orderDto); 
            //buy wird aufgerufen
            verify(orderService).transaktionBuy(orderDto);
            //sell wird nicht aufgerufen
            verify(orderService, never()).transaktionSell(orderDto);
            verify(orderService).setzeUndSpeichereObjekte(orderDto);
        }   

    
        @Test
        void testeTransaktionWennOrderTypeSellIst(){
            orderDto = new OrderDto();
            orderDto.setOrderType("sell");

            doNothing().when(orderService).initialisiereObjekte(any(OrderDto.class));
            doNothing().when(orderService).transaktionBuy(any(OrderDto.class));
            doNothing().when(orderService).transaktionSell(any(OrderDto.class));
            doNothing().when(orderService).setzeUndSpeichereObjekte(any(OrderDto.class));
        
            orderService.transaktion(orderDto);

            verify(orderService).initialisiereObjekte(orderDto); 
            //buy wird nicht aufgerufen
            verify(orderService, never()).transaktionBuy(orderDto);
            //sell wird aufgerufen
            verify(orderService).transaktionSell(orderDto);
            verify(orderService).setzeUndSpeichereObjekte(orderDto);
        }

        @Test
        void testeTransaktionWennOrderTypeNichtDefiniertIst(){
            orderDto = new OrderDto();
            orderDto.setOrderType(null);

            doNothing().when(orderService).initialisiereObjekte(any(OrderDto.class));
            doNothing().when(orderService).transaktionBuy(any(OrderDto.class));
            doNothing().when(orderService).transaktionSell(any(OrderDto.class));
            doNothing().when(orderService).setzeUndSpeichereObjekte(any(OrderDto.class));
        
            Exception exception = assertThrows(RuntimeException.class, () -> {
                orderService.transaktion(orderDto);
            });

            assertEquals("Ungültiger OrderType: " + orderDto.getOrderType(), exception.getMessage());

            verify(orderService).initialisiereObjekte(orderDto); 
            //Alles folgende wird nicht aufgerufen
            verify(orderService, never()).transaktionBuy(orderDto);
            verify(orderService, never()).transaktionSell(orderDto);
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
    @DisplayName("Tests für die Methode transaktionBuy(OrderDto orderDto)")
    class TransaktionBuytests{
        @Test
void testeTransaktionBuyBerechnung() {
    // Arrange: Erstelle ein Mock für die Aktie und das Konto
    Aktie mockAktie = mock(Aktie.class);
    Konto mockKonto = mock(Konto.class);

    // Initialisiere OrderDto mit Testdaten
    OrderDto orderDto = new OrderDto();
    orderDto.setAnteile(10.0);
    orderDto.setLiveKurs(150.0);

    // Setze die Mock-Attribute für die Aktie
    when(mockAktie.getAnzahlAktienAnteile()).thenReturn(5.0);
    when(mockAktie.getBuyInKurs()).thenReturn(100.0);

    // Setze das Start-Guthaben des Kontos
    Double startGuthaben = 2000.0;
    when(mockKonto.getAktuellesKontoGuthaben()).thenReturn(startGuthaben);

    // Erstelle einen Spy für den OrderService
    OrderService orderServiceSpy = Mockito.spy(new OrderService(aktieRepository, orderRepository, portfolioRepository, kontoRepository));

    // Setze die Mock-Objekte in den Spy
    orderServiceSpy.aktie = mockAktie;
    orderServiceSpy.kontoGuthaben = startGuthaben;

    // Act: Führe die Methode aus
    orderServiceSpy.transaktionBuy(orderDto);

    // Assert: Verifiziere die Berechnungsergebnisse
    double erwarteteNeueAnzahl = 15.0; // 5 bestehend + 10 neu
    double erwarteteNeueInvestition = 10.0 * 150.0; // Neue Anteile * Live-Kurs
    double erwarteteGesamtInvestition = (5.0 * 100.0) + erwarteteNeueInvestition; // Bestehende + neue Investition
    double erwarteterNeuerBuyInKurs = erwarteteGesamtInvestition / erwarteteNeueAnzahl;

    // Überprüfe, ob die korrekten Werte gesetzt wurden
    verify(mockAktie).setAnzahlAktienAnteile(erwarteteNeueAnzahl);
    verify(mockAktie).setBuyInKurs(erwarteterNeuerBuyInKurs);

    // Verifiziere, dass Guthaben korrekt reduziert wurde
    double erwartetesNeuesGuthaben = startGuthaben - erwarteteNeueInvestition;
    assertEquals(erwartetesNeuesGuthaben, orderServiceSpy.kontoGuthaben);

    // Verifiziere, dass das Repository zum Speichern der Aktie aufgerufen wurde
    verify(aktieRepository).save(mockAktie);
}

    }
    
}
