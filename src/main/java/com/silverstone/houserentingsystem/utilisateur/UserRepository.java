package com.silverstone.houserentingsystem.utilisateur;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Utilisateur, Long> {

    @Query("select u from Utilisateur u")
    Optional<Utilisateur> findByEmail(String email);
}
