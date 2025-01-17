package com.Auro.ProjektAuro.service.aktie;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.Auro.ProjektAuro.repository.AktieRepository;
import com.Auro.ProjektAuro.service.aktien.AktiePosition;
import com.Auro.ProjektAuro.service.aktien.AktienService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AktienServiceTest {

    private AktieRepository aktieRepository;
    private AktienService aktienService;

    @BeforeEach
    void setUp() {
        aktieRepository = mock(AktieRepository.class);
        aktienService = new AktienService(aktieRepository);
    }

    @Test
    void testeGetPositionWennTickerExistiert() {
        String ticker = "AAPL";
        when(aktieRepository.existsById(ticker)).thenReturn(true);
        when(aktieRepository.getBuyInKursByTicker(ticker)).thenReturn(150.0);
        when(aktieRepository.getAnzahlAktienAnteileByTicker(ticker)).thenReturn(10.0);

        AktiePosition result = aktienService.getPosition(ticker);

        assertNotNull(result);
        assertEquals(150.0, result.getBuyInKurs());
        assertEquals(10.0, result.getAnzahlAktienAnteile());
        verify(aktieRepository).existsById(ticker);
        verify(aktieRepository).getBuyInKursByTicker(ticker);
        verify(aktieRepository).getAnzahlAktienAnteileByTicker(ticker);
    }

    @Test
    void testeGetPositionWennTickerNichtExisitert() {
        String ticker = "AAPL";
        when(aktieRepository.existsById(ticker)).thenReturn(false);

        AktiePosition result = aktienService.getPosition(ticker);

        assertNotNull(result);
        assertNull(result.getBuyInKurs());
        assertNull(result.getAnzahlAktienAnteile());
        verify(aktieRepository).existsById(ticker);
        verifyNoMoreInteractions(aktieRepository);
    }

    @Test
    void testeGetPositionWennRepositoryEineExceptionWirft() {
        String ticker = "AAPL";
        when(aktieRepository.existsById(ticker)).thenReturn(true);
        when(aktieRepository.getBuyInKursByTicker(ticker)).thenThrow(new RuntimeException("Database error"));

        AktiePosition result = aktienService.getPosition(ticker);

        assertNotNull(result);
        assertNull(result.getBuyInKurs());
        assertNull(result.getAnzahlAktienAnteile());
        verify(aktieRepository).existsById(ticker);
        verify(aktieRepository).getBuyInKursByTicker(ticker);
    }
}
