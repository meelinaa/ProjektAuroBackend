package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.OrderDto;
import com.Auro.ProjektAuro.service.order.OrderService;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
@NoArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/transaktion")
    public ResponseEntity<String> saveTransaktion(@RequestBody OrderDto orderDto) {
        if (orderDto == null) {
            throw new IllegalArgumentException("OrderDto darf nicht leer sein oder fehlen");
        }
        try {
            orderService.transaktion(orderDto);
            System.out.println("Empfangene Transaktion: " + orderDto);
            return ResponseEntity.ok("Transaktion gespeichert");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        
    }

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getTransaktionen() {
        try {
            return ResponseEntity.ok(orderService.getTransaktionen());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }   
    
}