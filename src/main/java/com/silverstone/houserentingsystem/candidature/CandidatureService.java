package com.silverstone.houserentingsystem.candidature;

import com.silverstone.houserentingsystem.exceptions.AucuneCandidatureException;
import com.silverstone.houserentingsystem.exceptions.OffreInexistantException;
import com.silverstone.houserentingsystem.exceptions.StatusInexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;

import java.util.List;

public interface CandidatureService {

    Candidature deposerUneCandidature(CandidatureDto candidatureDto) throws StatusInexistantException, UtilisateurInexistantException, OffreInexistantException;
    List<Candidature> listeToutesLesCandidatures();
    List<Candidature> candidatureParUtilisateur(Long idUser) throws AucuneCandidatureException;
    Candidature candidatureParIdCandidature(Long idCandidature) throws AucuneCandidatureException;
    Boolean supprimerUneCandidature(Long idCandidature) throws AucuneCandidatureException;
}
