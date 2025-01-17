package com.Auro.ProjektAuro.service.konto;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.Auro.ProjektAuro.repository.KontoRepository;

@Service
public class KontoService {

    private KontoRepository kontoRepository;

    private Integer kontoID = 1;

    public KontoService(KontoRepository kontoRepository){
        this.kontoRepository = kontoRepository;
    }

    public Double getKontoGuthaben() {
        if(!kontoRepository.existsById(kontoID)){
            throw new NoSuchElementException("Es existiert kein Konto mit dieser Id");
        }
        Double guthaben = kontoRepository.getGuthaben(kontoID);
        if (guthaben == null) {
            throw new IllegalStateException("Kein Guthaben für das Konto gefunden");
        }
        return guthaben;
    }
    

    public String getKontoName() {
        if(!kontoRepository.existsById(kontoID)){
            throw new NoSuchElementException("Es existiert kein Konto mit dieser Id");
        }
        String name = kontoRepository.getName(kontoID);
        if (name == null) {
            throw new IllegalStateException("Kein Name für das Konto gefunden");
        }
        return name;
    }
    
}
