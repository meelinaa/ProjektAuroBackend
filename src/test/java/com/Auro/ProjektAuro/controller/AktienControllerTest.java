package com.Auro.ProjektAuro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Auro.ProjektAuro.service.aktien.AktiePosition;
import com.Auro.ProjektAuro.service.aktien.AktienService;
import com.Auro.ProjektAuro.service.external.AktienScrapping;
import com.Auro.ProjektAuro.service.external.AktienScrappingDatenInfos;
import com.Auro.ProjektAuro.service.external.AktienScrappingDatenLive;

public class AktienControllerTest {

    AktienController aktienController;
    AktienScrapping aktienScrapping;
    AktienService aktienService;

    String ticker = "AAPL";

    @BeforeEach
    void setUp(){
        aktienScrapping = mock(AktienScrapping.class);
        aktienService = mock(AktienService.class);
        aktienController = new AktienController(aktienScrapping, aktienService);
    }

    @Nested
    @DisplayName("Tests für die Methode getInfosAktien()")
    class GetInfosAktienTest{

        @Test
        void getInfosAktienMitKorrekterAusgabe(){
            AktienScrappingDatenInfos daten = new AktienScrappingDatenInfos(
                "Apple Inc.",
                "140.00 - 150.00",
                "100.00 - 180.00",
                "1,000,000",
                "2 Trillion",
                "25.0",
                "170.0",
                "2.5%",
                ticker
            );
            
            when(aktienScrapping.loadStockInfos(ticker)).thenReturn(daten);

            ResponseEntity<AktienScrappingDatenInfos> response = aktienController.getInfosAktien(ticker);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
        }

        @Test
        void getInfosAktienWennFehlermeldungWennTickerGleichNull(){
            ticker = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                aktienController.getInfosAktien(ticker);
            });
            
            assertEquals("Der Ticker darf nicht leer sein oder fehlen", exception.getMessage());
            
            verify(aktienScrapping, never()).loadStockInfos(ticker);
        }

        @Test
        void getInfosAktienWennFehlermeldungWennTickerLeerIst(){
            ticker = "";

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                aktienController.getInfosAktien(ticker);
            });
            
            assertEquals("Der Ticker darf nicht leer sein oder fehlen", exception.getMessage());
            
            verify(aktienScrapping, never()).loadStockInfos(ticker);
        }

        @Test
        void getInfosAktienWennFehlermeldungWennResponseEntityNichtOKist(){
            when(aktienScrapping.loadStockInfos(ticker)).thenThrow(new RuntimeException("Fehler beim Laden der Daten"));

            ResponseEntity<?> response = aktienController.getInfosAktien(ticker);

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getLiveZahlenAktien()")
    class GetLiveZahlenAktienTest{

        @Test
        void getLiveZahlenAktienMitKorrekterAusgabe(){
            AktienScrappingDatenLive daten = new AktienScrappingDatenLive(
                150.75,    
                2.35,      
                3.55,     
                "AAPL"    
            );

            when(aktienScrapping.reloadStockPrice(ticker)).thenReturn(daten);

            ResponseEntity<AktienScrappingDatenLive> response = aktienController.getLiveZahlenAktien(ticker);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
        }

        @Test
        void getLiveZahlenAktienFehlermeldungWennTickerGleichNull(){
            ticker = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                aktienController.getLiveZahlenAktien(ticker);
            });
            
            assertEquals("Der Ticker darf nicht leer sein oder fehlen", exception.getMessage());
            
            verify(aktienScrapping, never()).reloadStockPrice(ticker);
        }

        @Test
        void getLiveZahlenAktienFehlermeldungWennTickerLeerIst(){
            ticker = "";

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                aktienController.getLiveZahlenAktien(ticker);
            });
            
            assertEquals("Der Ticker darf nicht leer sein oder fehlen", exception.getMessage());
            
            verify(aktienScrapping, never()).reloadStockPrice(ticker);
        }

        @Test
        void getLiveZahlenAktienWennFehlermeldungWennResponseEntityNichtOKist(){
            when(aktienScrapping.reloadStockPrice(ticker)).thenThrow(new RuntimeException("Fehler beim Laden der Daten"));

            ResponseEntity<?> response = aktienController.getLiveZahlenAktien(ticker);

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }
    }

    @Nested
    @DisplayName("Tests für die Methode getPosition()")
    class GetPositionTest{

        @Test
        void getPositionMitKorrekterAusgabe(){
            AktiePosition daten = new AktiePosition(150.75, 10.0);

            when(aktienService.getPosition(ticker)).thenReturn(daten);

            ResponseEntity<AktiePosition> response = aktienController.getPosition(ticker);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(daten, response.getBody());
        }

        @Test
        void getPositionFehlermeldungWennTickerGleichNull(){
            ticker = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                aktienController.getPosition(ticker);
            });
            
            assertEquals("Der Ticker darf nicht leer sein oder fehlen", exception.getMessage());
            
            verify(aktienScrapping, never()).reloadStockPrice(ticker);
        }

        @Test
        void getPositionFehlermeldungWennTickerLeerIst(){
            ticker = "";

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                aktienController.getPosition(ticker);
            });
            
            assertEquals("Der Ticker darf nicht leer sein oder fehlen", exception.getMessage());
            
            verify(aktienScrapping, never()).reloadStockPrice(ticker);
        }

        @Test
        void getPositionFehlermeldungWennResponseEntityNichtOKist(){
            when(aktienService.getPosition(ticker)).thenThrow(new RuntimeException("Fehler beim Laden der Daten"));

            ResponseEntity<?> response = aktienController.getPosition(ticker);

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }
    }
    
}


