package com.Auro.ProjektAuro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
    
    private String ticker;
    private String orderType;
    private Double liveKurs;
    private Double anteile;
    private String companyName;

}
