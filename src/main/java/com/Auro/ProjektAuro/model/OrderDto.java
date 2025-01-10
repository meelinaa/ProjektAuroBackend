package com.Auro.ProjektAuro.model;

import lombok.Data;

@Data
public class OrderDto {
    
    private String ticker;
    private String orderType;
    private Double liveKurs;
    private Double anteile;
    private String companyName;

}
