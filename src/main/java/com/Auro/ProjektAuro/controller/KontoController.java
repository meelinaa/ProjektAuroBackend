package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.service.konto.KontoService;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/konto")
public class KontoController {
    
    private KontoService kontoService;

    public KontoController(KontoService kontoService){
        this.kontoService = kontoService;
    }

    @GetMapping("/guthaben")
    public ResponseEntity<Double> getGuthaben() {
        try{
            Double guthaben = kontoService.getKontoGuthaben();
            Double roundedGuthaben = BigDecimal.valueOf(guthaben)
                                   .setScale(2, RoundingMode.HALF_UP)
                                   .doubleValue();
            if (guthaben != null) {
                return ResponseEntity.ok(roundedGuthaben);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/name")
    public ResponseEntity<String> getKontoName() {
        try {
            String name = kontoService.getKontoName();
            if (name != null) {
                return ResponseEntity.ok(name);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
    
}
