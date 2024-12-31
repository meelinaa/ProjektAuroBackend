package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.service.aktien.AktiePosition;
import com.Auro.ProjektAuro.service.aktien.AktienService;
import com.Auro.ProjektAuro.service.external.AktienScrapping;
import com.Auro.ProjektAuro.service.external.AktienScrappingDatenInfos;
import com.Auro.ProjektAuro.service.external.AktienScrappingDatenLive;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/aktie")
public class AktienController {

    private AktienScrapping aktienScrapping;
    private AktienService aktienService;

    public AktienController(AktienScrapping aktienScrapping,
                            AktienService aktienService){
        this.aktienScrapping = aktienScrapping;
        this.aktienService = aktienService;
    }    

    @GetMapping("/{ticker}/infos")
    public AktienScrappingDatenInfos getInfosAktien(@PathVariable String ticker) {
        return aktienScrapping.loadStockInfos(ticker);
    }

    @GetMapping("/{ticker}/live")
    public AktienScrappingDatenLive getLiveZahlenAktien(@PathVariable String ticker) {
        return aktienScrapping.reloadStockPrice(ticker);
    }   

    @GetMapping("/{ticker}/positionen")
    public AktiePosition getPosition(@PathVariable String ticker) {
        return aktienService.getPosition(ticker);
    }
    
}