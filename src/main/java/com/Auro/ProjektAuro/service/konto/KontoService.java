package com.Auro.ProjektAuro.service.konto;

import org.springframework.stereotype.Service;

import com.Auro.ProjektAuro.repository.KontoRepository;

@Service
public class KontoService {

    private KontoRepository kontoRepository;

    public KontoService(KontoRepository kontoRepository){
        this.kontoRepository = kontoRepository;
    }

    public Double getKontoGuthaben() {
        Integer kontoID = 1;
        return kontoRepository.getGuthaben(kontoID);
    }
    
}
