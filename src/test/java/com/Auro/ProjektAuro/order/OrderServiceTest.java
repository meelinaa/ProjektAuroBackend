package com.Auro.ProjektAuro.order;

import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.Auro.ProjektAuro.repository.AktieRepository;
import com.Auro.ProjektAuro.repository.KontoRepository;
import com.Auro.ProjektAuro.repository.OrderRepository;
import com.Auro.ProjektAuro.repository.PortfolioRepository;
import com.Auro.ProjektAuro.service.aktien.AktienService;
import com.Auro.ProjektAuro.service.order.OrderService;

public class OrderServiceTest {

    private OrderService orderService;
    private OrderRepository orderRepository;
    private AktieRepository aktieRepository;
    private PortfolioRepository portfolioRepository;
    private KontoRepository kontoRepository;

    @BeforeEach
    void setUp(){
        orderRepository = mock(OrderRepository.class);
        aktieRepository = mock(AktieRepository.class);
        portfolioRepository = mock(PortfolioRepository.class);
        kontoRepository = mock(KontoRepository.class);

        orderService = new OrderService(aktieRepository, orderRepository, portfolioRepository, kontoRepository);
    }


    
}
