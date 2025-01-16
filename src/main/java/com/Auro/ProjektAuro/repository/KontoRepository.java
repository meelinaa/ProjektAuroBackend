package com.Auro.ProjektAuro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Auro.ProjektAuro.model.Konto;

public interface KontoRepository extends JpaRepository<Konto, Integer>{

    @Query("SELECT k.aktuellesKontoGuthaben FROM Konto k WHERE k.id = :id")
    Double getGuthaben(Integer id);

    @Query("SELECT n.name FROM Konto n WHERE n.id = :id")
    String getName(Integer id);

}
