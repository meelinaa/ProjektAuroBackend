package com.Auro.ProjektAuro.service.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.repository.AktieRepository;
import com.Auro.ProjektAuro.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private AktieRepository aktieRepository;

    @Autowired
    private OrderRepository orderRepository;

    // -- Aktie kauf
    // Speichere Die daten in Order
    // füge eine buy / sell info hinzu
    // füge datum hinzu
    // füge aktie ticker hinzu
    // füge id vom portfolio hinzu
    // speichere die daten in "aktie" - via repository
    
    public void buyAktie(String ticker, Double anteile, Double liveKurs) {
        
    }
    
   
}

