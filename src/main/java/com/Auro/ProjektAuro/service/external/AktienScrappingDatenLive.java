package com.Auro.ProjektAuro.service.external;

import lombok.Data;

@Data
public class AktienScrappingDatenLive {
    private Double regularMarketPrice;
    private Double regularMarketChangePercent;
    private Double regularMarketChange;
    private String ticker;

    public AktienScrappingDatenLive(Double regularMarketPrice,
                                Double regularMarketChangePercent,
                                Double regularMarketChange,
                                String ticker)
    {
        this.regularMarketPrice = regularMarketPrice;
        this.regularMarketChangePercent = regularMarketChangePercent;
        this.regularMarketChange = regularMarketChange;
        this.ticker = ticker;
    }
}
