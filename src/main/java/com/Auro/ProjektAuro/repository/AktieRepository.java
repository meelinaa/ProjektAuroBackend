package com.Auro.ProjektAuro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Auro.ProjektAuro.model.Aktie;

@Repository
public interface AktieRepository extends JpaRepository<Aktie, String>{

    @Query("SELECT a.buyInKurs FROM Aktie a WHERE a.id = :ticker")
    Double getBuyInKursByTicker(@Param("ticker") String ticker);

    @Query("SELECT a.anzahlAktienAnteile FROM Aktie a WHERE a.id = :ticker")
    Double getAnzahlAktienAnteileByTicker(@Param("ticker") String ticker);
    
}
