package com.Auro.ProjektAuro.service.external;

// import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// import com.Auro.ProjektAuro.repository.AktieRepository;


@Service
public class AktienScrapping {

    
    private AktienWebScrapping aktienWebScrapping;

    //@Autowired
    //private AktieRepository aktieRepository;

    
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