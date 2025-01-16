package com.Auro.ProjektAuro.service.konto;

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
        return kontoRepository.getGuthaben(kontoID);
    }

    public String getKontoName() {
        return kontoRepository.getName(kontoID);
    }
    
}
