package com.Auro.ProjektAuro.service.external;

import org.springframework.stereotype.Service;

@Service
public class AktienScrapping {

    private AktienWebScrapping aktienWebScrapping;
    
    public AktienScrapping (AktienWebScrapping aktienWebScrapping){
        this.aktienWebScrapping = aktienWebScrapping;
    }

    public AktienScrappingDatenInfos loadStockInfos(String ticker){
        return aktienWebScrapping.scrapeStockInfos(ticker);
    }

    public AktienScrappingDatenLive reloadStockPrice(String ticker){
        AktienScrappingDatenLive liveDaten = aktienWebScrapping.reloadStockPriceMarket(ticker);

        return liveDaten;
    }
    
}