package com.Auro.ProjektAuro.service.order;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransaktionenDto {

    private LocalDateTime orderDateAndTime;
    private String aktienTicker;
    private String aktienName;
    private String orderType;
    private Double aktie_anteile;
    private Double buySellKurs;

    public TransaktionenDto(LocalDateTime orderDateAndTime,
                            String aktienTicker,
                            String aktienName,
                            String orderType,
                            Double aktie_anteile,
                            Double buySellKurs)
    {
        this.orderDateAndTime = orderDateAndTime;
        this.aktienTicker = aktienTicker;
        this.aktienName = aktienName;
        this.orderType = orderType;
        this.aktie_anteile = aktie_anteile;
        this.buySellKurs = buySellKurs;
    }
    
}