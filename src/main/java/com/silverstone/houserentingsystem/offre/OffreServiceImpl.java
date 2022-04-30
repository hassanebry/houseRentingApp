package com.silverstone.houserentingsystem.offre;

import com.silverstone.houserentingsystem.bien.Bien;
import com.silverstone.houserentingsystem.bien.BienRepository;
import com.silverstone.houserentingsystem.exceptions.BienInnexistantException;
import com.silverstone.houserentingsystem.exceptions.OffreInexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import com.silverstone.houserentingsystem.utilisateur.UserRepository;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OffreServiceImpl implements OffreService{

    private final OffreRepository offreRepository;
    private final UserRepository userRepository;
    private final BienRepository bienRepository;

    @Override
    public Offre deposerUneOffre(OffreDto offreDto) throws UtilisateurInexistantException, BienInnexistantException {

        Optional<Utilisateur> utilisateurOptional = userRepository.findById(offreDto.getIdUtilisateur());
        Optional<Bien> bienOptional = bienRepository.findById(offreDto.getIdBienConcerne());
        if (utilisateurOptional.isEmpty()){
            throw new UtilisateurInexistantException();
        }
        if (bienOptional.isEmpty()){
            throw new BienInnexistantException();
        }

        Offre offre = new Offre(offreDto.getPrix(),
                offreDto.getDescription(),
                offreDto.getDisponibleLe());

        offre.setUtilisateur(utilisateurOptional.get());
        offre.setBienConcerne(bienOptional.get());
        offreRepository.save(offre);
        return offre;
    }

    @Override
    public Offre chercherUneOffreParId(Long idOffre) throws OffreInexistantException {
        Optional<Offre> offreOptional = offreRepository.findById(idOffre);
        if (offreOptional.isEmpty()){
            throw new OffreInexistantException();
        }
        return offreOptional.get();
    }

    @Override
    public void supprimerUneOffre(Long idOffre) throws OffreInexistantException {
        Optional<Offre> offreOptional = offreRepository.findById(idOffre);
        if (offreOptional.isEmpty()){
            throw new OffreInexistantException();
        }
        offreRepository.deleteById(idOffre);
    }

    @Override
    public Collection<Offre> afficherToutesLesOffres() {
        return offreRepository.findAll();
    }
}
