package com.Auro.ProjektAuro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Auro.ProjektAuro.model.Aktie;
import com.Auro.ProjektAuro.model.Order;
import com.Auro.ProjektAuro.model.Portfolio;

public interface PortfolioRepository extends JpaRepository<Portfolio,Integer>{
    
    @Query("SELECT a FROM Aktie a WHERE a.portfolio.id = :id")
    List<Aktie> findAllAktienById(@Param("id") Integer id);

    @Query("SELECT o FROM Order o WHERE o.portfolio.id = :id")
    List<Order> findAllOrdersById(@Param("id") Integer id);
   
}
