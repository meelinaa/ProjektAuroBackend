package com.Auro.ProjektAuro.service.order;

import lombok.Data;

@Data
class OrderBerechnen {

    private double anzahlAktienAnteileAlt;
    private double buyInKursAlt;
    private double anzahlAktienAnteileNeu;
    private double buyInKursNeu;

    public OrderBerechnen(double anzahlAktienAnteileAlt,
                        double buyInKursAlt,
                        double anzahlAktienAnteileNeu,
                        double buyInKursNeu){
        this.anzahlAktienAnteileAlt = anzahlAktienAnteileAlt;
        this.buyInKursAlt = buyInKursAlt;
        this.anzahlAktienAnteileNeu = anzahlAktienAnteileNeu;
        this.buyInKursNeu = buyInKursNeu;
    }

}
