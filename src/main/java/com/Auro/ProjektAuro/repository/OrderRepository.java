package com.Auro.ProjektAuro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Auro.ProjektAuro.model.Order;

public interface OrderRepository extends JpaRepository<Order,Integer>{
    
}
