package com.Auro.ProjektAuro.service.external;

import org.springframework.stereotype.Service;

import com.Auro.ProjektAuro.repository.AktieRepository;

import com.Auro.ProjektAuro.repository.AktieRepository;

@Service
public class AktienScrapping {

    
    private AktienWebScrapping aktienWebScrapping;
    private AktieRepository aktieRepository;

    
    public AktienScrapping (AktienWebScrapping aktienWebScrapping,
                            AktieRepository aktieRepository){
        this.aktienWebScrapping = aktienWebScrapping;
        this.aktieRepository = aktieRepository;
    }

    public AktienScrappingDatenInfos loadStockInfos(String ticker){
        return aktienWebScrapping.scrapeStockInfos(ticker);
    }

    public AktienScrappingDatenLive reloadStockPrice(String ticker){
        AktienScrappingDatenLive liveDaten = aktienWebScrapping.reloadStockPriceMarket(ticker);

        return liveDaten;
    }
}