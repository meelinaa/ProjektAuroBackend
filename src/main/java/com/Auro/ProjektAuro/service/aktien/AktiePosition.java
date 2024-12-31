package com.Auro.ProjektAuro.service.aktien;

import lombok.Data;

@Data
public class AktiePosition {
    private Double aktuellerKurs;
    private Double buyInKurs;
    private Double anzahlAktienAnteile;

    public AktiePosition(Double aktuellerKurs,
                        Double buyInKurs,
                        Double anzahlAktienAnteile){
        this.aktuellerKurs = aktuellerKurs;
        this.buyInKurs = buyInKurs;
        this.anzahlAktienAnteile = anzahlAktienAnteile;
    }
}
