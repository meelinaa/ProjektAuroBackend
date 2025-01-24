package com.Auro.ProjektAuro.service.external;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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

}