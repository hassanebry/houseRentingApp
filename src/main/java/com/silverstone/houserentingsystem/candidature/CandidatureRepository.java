package com.silverstone.houserentingsystem.candidature;

import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Long> {
    @Query("select c from Candidature  c where c.utilisateur.userId=?1")
    List<Candidature> findByUserId(Long userId);
}
