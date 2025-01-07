package com.Auro.ProjektAuro.service.aktien;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Auro.ProjektAuro.repository.AktieRepository;

@Service
public class AktienService {

    @Autowired
    private AktieRepository aktieRepository;

    public AktiePosition getPosition(String ticker) {
        if (!aktieRepository.existsById(ticker)) {
            return new AktiePosition(null, null);
        }

        try {
            Double buyInKurs = aktieRepository.getBuyInKursByTicker(ticker);
            Double anzahlAktienAnteile = aktieRepository.getAnzahlAktienAnteileByTicker(ticker);

            return new AktiePosition(buyInKurs, anzahlAktienAnteile);
        } catch (Exception e) {
            System.err.println("Fehler beim Abrufen der Aktie: " + e.getMessage());
            return new AktiePosition(null, null);
        }
    }


}
