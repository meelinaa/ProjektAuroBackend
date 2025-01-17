package com.Auro.ProjektAuro.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AktienControllerTest {

    @Test
    void twoPlusTwouSHouldEqualFour(){
        var controller = new AktienController(null, null);

        assertEquals(4, controller.add(2,2)); 

    }
    
}
