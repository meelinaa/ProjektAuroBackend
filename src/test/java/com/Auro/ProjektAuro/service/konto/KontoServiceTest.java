package com.Auro.ProjektAuro.service.konto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Auro.ProjektAuro.repository.KontoRepository;

public class KontoServiceTest {

    private KontoRepository kontoRepository;
    private KontoService kontoService;

    private Integer kontoID = 1;

    @BeforeEach
    void setUp(){
        kontoRepository = mock(KontoRepository.class);
        kontoService = new KontoService(kontoRepository);
    }

    // getKontoGuthaben
    @Test
    void testeGetKontoGuthabenMitKorrekterAusgabe(){
        kontoID = 1;
        when(kontoRepository.getGuthaben(kontoID)).thenReturn(25000.00);
        when(kontoRepository.existsById(kontoID)).thenReturn(true);
        
        Double guthaben = kontoService.getKontoGuthaben();

        assertNotNull(guthaben);
        assertEquals(25000.00, guthaben);
        verify(kontoRepository).getGuthaben(kontoID);
    }

    @Test
    void testeGetKontoGuthabenWennGuthabenNULLIstUndEinExceptionGeworfenWird(){
        when(kontoRepository.existsById(kontoID)).thenReturn(true);
        when(kontoRepository.getGuthaben(kontoID)).thenReturn(null);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            kontoService.getKontoGuthaben();
        });

        assertEquals("Kein Guthaben für das Konto gefunden", exception.getMessage());
        verify(kontoRepository).getGuthaben(kontoID);
    }

    @Test
    void testeGetKontoGuthabenWennKontoUnterDerIDNichtExistiert(){
        when(kontoRepository.existsById(kontoID)).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            kontoService.getKontoGuthaben();
        });

        assertEquals("Es existiert kein Konto mit dieser Id", exception.getMessage());
        verify(kontoRepository, never()).getGuthaben(kontoID);
    }

    // getKontoName
    @Test
    void testeGetKontoNameMitKorrekterAusgabe(){
        when(kontoRepository.getName(kontoID)).thenReturn("Max Mustermann");
        when(kontoRepository.existsById(kontoID)).thenReturn(true);
        
        String name = kontoService.getKontoName();

        assertNotNull(name);
        assertEquals("Max Mustermann", name);
        verify(kontoRepository).getName(kontoID);
    }

    @Test
    void testeGetKontoNameWennNameNULLIstUndEinExceptionGeworfenWird(){
        when(kontoRepository.getName(kontoID)).thenReturn(null);
        when(kontoRepository.existsById(kontoID)).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () -> {
            kontoService.getKontoName();
        });

        assertEquals("Kein Name für das Konto gefunden", exception.getMessage());
        verify(kontoRepository).getName(kontoID);
    }

    @Test
    void testeGetKontoNameWennKontoUnterDerIDNichtExistiert(){
        when(kontoRepository.existsById(kontoID)).thenReturn(false);

        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            kontoService.getKontoName();
        });

        assertEquals("Es existiert kein Konto mit dieser Id", exception.getMessage());
        verify(kontoRepository, never()).getGuthaben(kontoID);
    }

}
