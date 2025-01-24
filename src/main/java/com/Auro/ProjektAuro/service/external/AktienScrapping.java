package com.Auro.ProjektAuro.service.external;

import org.springframework.stereotype.Service;

@Service
public class AktienScrapping {

    public AktienWebScrapping aktienWebScrapping;
    
    public AktienScrapping (AktienWebScrapping aktienWebScrapping){
        this.aktienWebScrapping = aktienWebScrapping;
    }

    public AktienScrappingDatenInfos loadStockInfos(String ticker){
        if ( ticker == null || ticker.isEmpty()) {
            throw new RuntimeException("Ticker darf nicht null oder leer sein");
        }
        try {
            return aktienWebScrapping.scrapeStockInfos(ticker);
        } catch (Exception e) {
            throw new IllegalArgumentException("Es konnten die Daten nicht geladen werden");
        }
    }

    public AktienScrappingDatenLive reloadStockPrice(String ticker){
        if ( ticker == null || ticker.isEmpty()) {
            throw new RuntimeException("Ticker darf nicht null oder leer sein");
        }
        try {
            AktienScrappingDatenLive liveDaten = aktienWebScrapping.reloadStockPriceMarket(ticker);
            return liveDaten;
        } catch (Exception e) {
            throw new IllegalArgumentException("Es konnten die Daten nicht geladen werden");
        }
        
    }
    
}