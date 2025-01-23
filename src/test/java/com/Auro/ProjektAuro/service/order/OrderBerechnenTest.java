package com.Auro.ProjektAuro.service.order;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class OrderBerechnenTest {

    private double anzahlAktienAnteileAlt;
    private double buyInKursAlt;
    private double anzahlAktienAnteileNeu;
    private double buyInKursNeu;

    private OrderBerechnen orderBerechnen;

    @Test
    void orderBerechnenKonstruktorTest(){
        anzahlAktienAnteileAlt = 20;
        buyInKursAlt = 120;
        anzahlAktienAnteileNeu = 30;
        buyInKursNeu = 140;

        orderBerechnen = new OrderBerechnen(anzahlAktienAnteileAlt, buyInKursAlt, anzahlAktienAnteileNeu, buyInKursNeu);

        assertEquals(anzahlAktienAnteileAlt, orderBerechnen.getAnzahlAktienAnteileAlt());
        assertEquals(buyInKursAlt, orderBerechnen.getBuyInKursAlt());
        assertEquals(anzahlAktienAnteileNeu, orderBerechnen.getAnzahlAktienAnteileNeu());
        assertEquals(buyInKursNeu, orderBerechnen.getBuyInKursNeu());
    }

    @Test
    void orderBerechnenSetterUndGetterTesten(){
        anzahlAktienAnteileAlt = 20;
        buyInKursAlt = 120;
        anzahlAktienAnteileNeu = 30;
        buyInKursNeu = 140;

        orderBerechnen = new OrderBerechnen();

        orderBerechnen.setAnzahlAktienAnteileAlt(anzahlAktienAnteileAlt);
        orderBerechnen.setBuyInKursAlt(buyInKursAlt);
        orderBerechnen.setAnzahlAktienAnteileNeu(anzahlAktienAnteileNeu);
        orderBerechnen.setBuyInKursNeu(buyInKursNeu);

        assertEquals(anzahlAktienAnteileAlt, orderBerechnen.getAnzahlAktienAnteileAlt());
        assertEquals(buyInKursAlt, orderBerechnen.getBuyInKursAlt());
        assertEquals(anzahlAktienAnteileNeu, orderBerechnen.getAnzahlAktienAnteileNeu());
        assertEquals(buyInKursNeu, orderBerechnen.getBuyInKursNeu());
    }
    
}
