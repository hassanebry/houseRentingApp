package com.silverstone.houserentingsystem.bien.caracteristique;

import com.silverstone.houserentingsystem.bien.enumeration.Energie;
import com.silverstone.houserentingsystem.bien.enumeration.Exposition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CaracteristiqueRepo extends JpaRepository<Caracteristique, Long> {

    @Query("select c From Caracteristique c where c.anneeConstruction = ?1 and c.balcon = ?2 and c.cave = ?3 and c.ascenseur = ?4 and c.gardien =?5 and c.energie=?6 and c.exposition=?7")
    Optional<Caracteristique> findByAllField(int anneeConstruction, boolean balcon, boolean cave, boolean ascenseur, boolean gardien, Energie energie, Exposition exposition);
}
