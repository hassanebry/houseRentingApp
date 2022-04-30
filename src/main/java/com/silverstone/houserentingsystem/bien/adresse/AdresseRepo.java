package com.silverstone.houserentingsystem.bien.adresse;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdresseRepo extends JpaRepository<Adresse, Long> {
    Optional<Adresse> findByRueAndCodepostalAndCommune(String rue, int codepostal, String commune);
}
