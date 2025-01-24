package com.Auro.ProjektAuro.service.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AktienScrappingDatenLive {

    private Double regularMarketPrice;
    private Double regularMarketChangePercent;
    private Double regularMarketChange;
    private String ticker;
    
}
