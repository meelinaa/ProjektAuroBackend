package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.service.konto.KontoService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


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
            if (guthaben != null) {
                return ResponseEntity.ok(guthaben);
            } else {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }
    }
    
}
