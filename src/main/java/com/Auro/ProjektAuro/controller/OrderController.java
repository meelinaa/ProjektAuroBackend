package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.service.order.OrderService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    //kaufen
    //if else logik falls es schon existiert
    @PutMapping("/${ticker}/kaufen/${anzahl}")
    public String postAktienKauf(@PathVariable String ticker, @PathVariable String anzahl) {
        orderService.aktienKaufen(ticker, anzahl);
        return "gekauft";
    }

    //verkaufen
    //if else logik falls es schon existiert
    @PutMapping("/${ticker}/verkaufen/${anzahl}")
    public String postAktienVerkauf(@RequestBody String ticker, @PathVariable String anzahl) {
        orderService.aktienVerkaufen(ticker, anzahl);
        return "verkauft";
    }
    

    //Übersicht
    @GetMapping("/uebersicht")
    public String getAlleOrder(@RequestParam String param) {
        return orderService.alleOderAnzeigen();
    }

    //Anzeige einer einzelnen Order
    // id, datum, aktie, anzahl, wert, buyIn, OrderArt(sell/buy)
    @GetMapping("/uebersicht/${id}")
    public String getEinzelneOrder(@PathVariable String id) {
        return orderService.einzelneOrder(id);
    }
}
