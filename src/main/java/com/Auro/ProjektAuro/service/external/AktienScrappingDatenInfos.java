package com.Auro.ProjektAuro.service.external;

import lombok.Data;

@Data
public class AktienScrappingDatenInfos {
    
    private String companyName;
    private String regularMarketDayRange;
    private String fiftyTwoWeekRange;
    private String regularMarketVolume;
    private String marketCap;
    private String trailingPE;
    private String targetMeanPrice;
    private String expectedDividend;
    private String ticker;

    public AktienScrappingDatenInfos(String companyName,
                                String regularMarketDayRange,
                                String fiftyTwoWeekRange,
                                String regularMarketVolume,
                                String marketCap,
                                String trailingPE,
                                String targetMeanPrice,
                                String expectedDividend,
                                String ticker)
    {
        this.companyName = companyName;
        this.regularMarketDayRange = regularMarketDayRange;
        this.fiftyTwoWeekRange = fiftyTwoWeekRange;
        this.regularMarketVolume = regularMarketVolume;
        this.marketCap = marketCap;
        this.trailingPE = trailingPE;
        this.targetMeanPrice = targetMeanPrice;
        this.expectedDividend = expectedDividend;
        this.ticker = ticker;
    }
}