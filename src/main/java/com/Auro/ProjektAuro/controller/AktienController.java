package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.service.aktien.AktiePosition;
import com.Auro.ProjektAuro.service.aktien.AktienService;
import com.Auro.ProjektAuro.service.external.AktienScrapping;
import com.Auro.ProjektAuro.service.external.AktienScrappingDatenInfos;
import com.Auro.ProjektAuro.service.external.AktienScrappingDatenLive;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/aktie")
@AllArgsConstructor
@NoArgsConstructor
public class AktienController {

    public AktienScrapping aktienScrapping;
    public AktienService aktienService;

    @GetMapping("/{ticker}/infos")
    public ResponseEntity<AktienScrappingDatenInfos> getInfosAktien(@PathVariable String ticker) {
        if (ticker == null || ticker.isEmpty()  ) {
            throw new IllegalArgumentException("Der Ticker darf nicht leer sein oder fehlen");
        }
        try {
            return ResponseEntity.ok(aktienScrapping.loadStockInfos(ticker));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/{ticker}/live")
    public ResponseEntity<AktienScrappingDatenLive> getLiveZahlenAktien(@PathVariable String ticker) {
        if (ticker == null || ticker.isEmpty()  ) {
            throw new IllegalArgumentException("Der Ticker darf nicht leer sein oder fehlen");
        }
        try {
            return ResponseEntity.ok(aktienScrapping.reloadStockPrice(ticker));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }   

    @GetMapping("/{ticker}/positionen")
    public ResponseEntity<AktiePosition> getPosition(@PathVariable String ticker) {
        if (ticker == null || ticker.isEmpty()  ) {
            throw new IllegalArgumentException("Der Ticker darf nicht leer sein oder fehlen");
        }
        try {
            return ResponseEntity.ok(aktienService.getPosition(ticker));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

   
    
}