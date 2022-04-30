package com.silverstone.houserentingsystem.candidature;

import com.silverstone.houserentingsystem.exceptions.AucuneCandidatureException;
import com.silverstone.houserentingsystem.exceptions.OffreInexistantException;
import com.silverstone.houserentingsystem.exceptions.StatusInexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import com.silverstone.houserentingsystem.offre.Offre;
import com.silverstone.houserentingsystem.offre.OffreRepository;
import com.silverstone.houserentingsystem.utilisateur.UserRepository;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CandidatureServiceImpl implements CandidatureService{

    private final CandidatureRepository candidatureRepository;
    private final UserRepository userRepository;
    private final OffreRepository offreRepository;


    @Override
    public Candidature deposerUneCandidature(CandidatureDto candidatureDto) throws StatusInexistantException, UtilisateurInexistantException, OffreInexistantException {
        Status status;
        if (candidatureDto.getStatus().equals("accepte")){
             status = Status.ACCEPTE;
        } else if (candidatureDto.getStatus().equals("rejete")) {
             status = Status.ACCEPTE;
        } else {
          throw new StatusInexistantException();
        }

        Optional<Utilisateur> userOptional = userRepository.findById(candidatureDto.getIdUtilisateur());
        Optional<Offre> offreOptional = offreRepository.findById(candidatureDto.getIdOffre());
        if (userOptional.isEmpty()){
            throw new UtilisateurInexistantException();
        }
        if (offreOptional.isEmpty()){
            throw new OffreInexistantException();
        }

        Candidature candidature = new Candidature(status);
        candidature.setUtilisateur(userOptional.get());
        candidature.setOffre(offreOptional.get());
        candidatureRepository.save(candidature);
        return candidature;
    }

    @Override
    public List<Candidature> listeToutesLesCandidatures() {
        return candidatureRepository.findAll();
    }

    @Override
    public List<Candidature> candidatureParUtilisateur(Long idUser) throws AucuneCandidatureException {
        List<Candidature> candidatures = candidatureRepository.findByUserId(idUser);
        if (candidatures.isEmpty()){
            throw new AucuneCandidatureException();
        }
        return candidatures;
    }

    @Override
    public Candidature candidatureParIdCandidature(Long idCandidature) throws AucuneCandidatureException {
        Optional<Candidature> optionalCandidature = candidatureRepository.findById(idCandidature);
        if (optionalCandidature.isEmpty()){
            throw new AucuneCandidatureException();
        }
        return optionalCandidature.get();
    }

    @Override
    public Boolean supprimerUneCandidature(Long idCandidature) throws AucuneCandidatureException {
        Optional<Candidature> optionalCandidature = candidatureRepository.findById(idCandidature);
        if (optionalCandidature.isEmpty()){
            throw new AucuneCandidatureException();
        }
        candidatureRepository.deleteById(idCandidature);
        return true;
    }
}
