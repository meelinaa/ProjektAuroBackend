package com.Auro.ProjektAuro.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Auro.ProjektAuro.model.OrderDto;
import com.Auro.ProjektAuro.service.order.OrderService;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;





@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/transaktion")
    public ResponseEntity<String> saveTransaktion(@RequestBody OrderDto orderDto) {
        System.out.println("Empfangene Transaktion: " + orderDto);
        orderService.transaktion(orderDto);
        return ResponseEntity.ok("Transaktion gespeichert");
    }
}
