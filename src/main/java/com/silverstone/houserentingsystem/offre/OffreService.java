package com.silverstone.houserentingsystem.offre;

import com.silverstone.houserentingsystem.exceptions.BienInnexistantException;
import com.silverstone.houserentingsystem.exceptions.OffreInexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;

import java.util.Collection;

public interface OffreService {

    Offre deposerUneOffre(OffreDto offreDto) throws UtilisateurInexistantException, BienInnexistantException;
    Offre chercherUneOffreParId(Long idOffre) throws OffreInexistantException;
    void supprimerUneOffre(Long idOffre) throws OffreInexistantException;
    Collection<Offre> afficherToutesLesOffres();

}
