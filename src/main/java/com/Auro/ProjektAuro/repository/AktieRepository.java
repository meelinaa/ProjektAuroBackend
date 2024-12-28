package com.Auro.ProjektAuro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Auro.ProjektAuro.model.Aktie;

@Repository
public interface AktieRepository extends JpaRepository<Aktie, String>{


}
