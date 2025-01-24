package com.Auro.ProjektAuro.external;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Portfolio;
import com.Auro.ProjektAuro.repository.PortfolioRepository;
import com.Auro.ProjektAuro.service.external.AktienScrapping;
import com.Auro.ProjektAuro.service.external.AktienScrappingDatenInfos;
import com.Auro.ProjektAuro.service.external.AktienScrappingDatenLive;
import com.Auro.ProjektAuro.service.external.AktienWebScrapping;
import com.Auro.ProjektAuro.service.portfolio.PortfolioService;

public class AktienScrappingTest {

    AktienWebScrapping aktienWebScrapping;
    AktienScrapping aktienScrapping;

    String ticker = "AAPL";

    @BeforeEach
    void setUp() {
        aktienWebScrapping =  mock(AktienWebScrapping.class); 
        aktienScrapping = new AktienScrapping(aktienWebScrapping);
        
    }
    
    @Nested
    @DisplayName("Tests für loadStockInfos()")
    class LoadStockInfosTests {

        @Test
        void loadStockInfosMitKorrektemReturn(){
            AktienScrappingDatenInfos daten = new AktienScrappingDatenInfos(
                                "Apple Inc.",
                                "140.00 - 150.00",
                                "100.00 - 180.00",
                                "1,000,000",
                                "2 Trillion",
                                "25.0",
                                "170.0",
                                "2.5%",
                                "AAPL"
            );

            when(aktienWebScrapping.scrapeStockInfos(ticker)).thenReturn(daten);
            AktienScrappingDatenInfos result = aktienScrapping.loadStockInfos(ticker);

            assertEquals(daten, result);
        }

        @Test
        void loadStockInfosMitFehlermeldungBeiLeeremTicker(){
            ticker = "";

            Exception exception = assertThrows(RuntimeException.class, () -> {
                aktienScrapping.loadStockInfos(ticker);
            });

            assertEquals("Ticker darf nicht null oder leer sein", exception.getMessage());
        }

        @Test
        void loadStockInfosMitFehlermeldungWennTickerNullIst(){
            ticker = null;

            Exception exception = assertThrows(RuntimeException.class, () -> {
                aktienScrapping.loadStockInfos(ticker);
            });

            assertEquals("Ticker darf nicht null oder leer sein", exception.getMessage());
        }

        @Test
        void loadStockInfosMitFehlermeldungWennCatchAufgerufenWird(){
            when(aktienScrapping.aktienWebScrapping.scrapeStockInfos(anyString())).thenThrow(new IllegalArgumentException("Es konnten die Daten nicht geladen werden"));


            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                aktienScrapping.loadStockInfos(ticker);
            });

            assertEquals("Es konnten die Daten nicht geladen werden", exception.getMessage());
        }
        
    }

    @Nested
    @DisplayName("Tests für reloadStockPrice()")
    class ReloadStockPriceTests {

        @Test
        void reloadStockPriceMitKorrektemReturn(){
            AktienScrappingDatenLive daten = new AktienScrappingDatenLive(
                150.75,  
                2.35,    
                3.55,     
                "AAPL"     
            );

            when(aktienWebScrapping.reloadStockPriceMarket(ticker)).thenReturn(daten);
            AktienScrappingDatenLive result = aktienScrapping.reloadStockPrice(ticker);

            assertEquals(daten, result);
        }

        @Test
        void reloadStockPriceMitFehlermeldungBeiLeeremTicker(){
            ticker = "";

            Exception exception = assertThrows(RuntimeException.class, () -> {
                aktienScrapping.reloadStockPrice(ticker);
            });

            assertEquals("Ticker darf nicht null oder leer sein", exception.getMessage());
        }

        @Test
        void reloadStockPriceMitFehlermeldungWennTickerNullIst(){
            ticker = null;

            Exception exception = assertThrows(RuntimeException.class, () -> {
                aktienScrapping.reloadStockPrice(ticker);
            });

            assertEquals("Ticker darf nicht null oder leer sein", exception.getMessage());
        }

        @Test
        void reloadStockPriceMitFehlermeldungWennCatchAufgerufenWird(){
            when(aktienScrapping.aktienWebScrapping.reloadStockPriceMarket(anyString())).thenThrow(new IllegalArgumentException("Es konnten die Daten nicht geladen werden"));

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                aktienScrapping.reloadStockPrice(ticker);
            });

            assertEquals("Es konnten die Daten nicht geladen werden", exception.getMessage());
        }
        


        
    }
    
    
}
