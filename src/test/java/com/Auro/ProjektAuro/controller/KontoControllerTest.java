package com.Auro.ProjektAuro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Auro.ProjektAuro.service.konto.KontoService;

public class KontoControllerTest {

    KontoService kontoService;
    KontoController kontoController;

    @BeforeEach
    void setUp(){
        kontoService = mock(KontoService.class);
        kontoController = new KontoController(kontoService);
    }

    @Nested
    @DisplayName("Tests für die Methode getGuthaben()")
    public class GetGuthabenTest {

        @Test
        void getGuthabenMitKorrekterAusgabe(){
            when(kontoService.getKontoGuthaben()).thenReturn(25000.59484);

            ResponseEntity<Double> response = kontoController.getGuthaben();
            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            //indirekt Test obs gerundet wurde
            assertEquals(25000.59, response.getBody());
        }

        @Test
        void getGuthabenMitFehlermeldungWennGuthabenGleichNULList(){
            when(kontoService.getKontoGuthaben()).thenReturn(null);

            ResponseEntity<Double> response = kontoController.getGuthaben();

            assertNotNull(response);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void getGuthabenMitFehlermeldungWennResponseEntityNichtOKist(){
            when(kontoService.getKontoGuthaben()).thenThrow(new RuntimeException("Fehler beim Laden der Daten"));

            ResponseEntity<Double> response = kontoController.getGuthaben();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
        
    }

    @Nested
    @DisplayName("Tests für die Methode getKontoName()")
    public class GetKontoNameTest {
        
        @Test
        void getKontoNameMitKorrekterAusgabe(){
            when(kontoService.getKontoName()).thenReturn("Gast");

            ResponseEntity<String> response = kontoController.getKontoName();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Gast", response.getBody());
        }

        @Test
        void getGuthabenMitFehlermeldungWennGuthabenGleichNULList(){
            when(kontoService.getKontoName()).thenReturn(null);

            ResponseEntity<String> response = kontoController.getKontoName();

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        void getGuthabenMitFehlermeldungWennResponseEntityNichtOKist(){
            when(kontoService.getKontoName()).thenThrow(new RuntimeException("Fehler beim Laden der Daten"));

            ResponseEntity<String> response = kontoController.getKontoName();

            assertNotNull(response);
            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertNull(response.getBody());
        }
        
    }
    
}
