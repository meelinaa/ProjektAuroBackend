package com.Auro.ProjektAuro.service.aktien;

import lombok.Data;

@Data
public class AktiePosition {
    private Double buyInKurs;
    private Double anzahlAktienAnteile;

    public AktiePosition(Double buyInKurs,
                        Double anzahlAktienAnteile){
        this.buyInKurs = buyInKurs;
        this.anzahlAktienAnteile = anzahlAktienAnteile;
    }
}
