package com.Auro.ProjektAuro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.OrderDto;
import com.Auro.ProjektAuro.service.order.OrderService;

public class OrderControllerTest {

    OrderService orderService;
    OrderController orderController;
    PrintStream mockOut;

    @BeforeEach
    void setUp(){
        orderService = mock(OrderService.class);
        orderController = new OrderController(orderService);

        mockOut = mock(PrintStream.class);
        System.setOut(mockOut);
    }

    @Nested
    @DisplayName("Tests für die Methode saveTransaktion()")
    public class SaveTransaktionTest {

        @Test
        void saveTransaktionMitKorrekterAusgabe(){
            OrderDto data = new OrderDto(
                "AAPL",           
                "buy",           
                150.75,          
                10.0,             
                "Apple Inc."      
            );

            doNothing().when(orderService).transaktion(data);

            ResponseEntity<String> response = orderController.saveTransaktion(data);

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals("Transaktion gespeichert", response.getBody());

            verify(mockOut).println("Empfangene Transaktion: " + data);

            verify(orderService).transaktion(data);            
        }

        @Test
        void saveTransaktionWennOrderDtoNullIst(){
            OrderDto orderDto = null;

            Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                orderController.saveTransaktion(orderDto);
            });
            
            assertEquals("OrderDto darf nicht leer sein oder fehlen", exception.getMessage());
        }

        @Test
        void saveTransaktionFehlermeldungWennResponseEntityNichtOKist(){
            OrderDto data = new OrderDto(
                "AAPL",           
                "buy",           
                150.75,          
                10.0,             
                "Apple Inc."      
            );
            doThrow(new RuntimeException("Fehler beim Laden der Daten"))
                    .when(orderService)
                    .transaktion(data);

            ResponseEntity<?> response = orderController.saveTransaktion(data);

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }
    
        
    }
    

    @Nested
    @DisplayName("Tests für die Methode getTransaktionen()")
    public class GetTransaktionenTest{

        @Test
        void getTransaktionenMitKorrekterAusgabe(){
            List<Order> orders = List.of(
                new Order(
                    1,                              
                    null,                    
                    LocalDateTime.now(),            
                    "AAPL",            
                    "Apple Inc.",        
                    "buy",                
                    10.0,             
                    150.75              
                ),
                new Order(
                    2,                           
                    null,                 
                    LocalDateTime.now().minusDays(1),
                    "TSLA",                  
                  "Tesla Inc.",                
                    "sell",                         
                    5.0,                        
                    750.50                        
                )
            );

            when(orderService.getTransaktionen()).thenReturn(orders);
            ResponseEntity<List<Order>> response = orderController.getTransaktionen();

            assertNotNull(response);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            verify(orderService).getTransaktionen();            
        }

        @Test
        void getTransaktionenFehlermeldungWennResponseEntityNichtOKist(){
            when(orderService.getTransaktionen()).thenThrow(new RuntimeException("Fehler beim Laden der Daten"));

            ResponseEntity<?> response = orderController.getTransaktionen();

            assertNotNull(response);
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());

        }
    }
}
