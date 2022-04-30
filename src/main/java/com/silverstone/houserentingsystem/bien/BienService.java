package com.silverstone.houserentingsystem.bien;

import com.silverstone.houserentingsystem.bien.adresse.Adresse;
import com.silverstone.houserentingsystem.bien.adresse.AdresseRepo;
import com.silverstone.houserentingsystem.bien.caracteristique.Caracteristique;
import com.silverstone.houserentingsystem.bien.caracteristique.CaracteristiqueRepo;
import com.silverstone.houserentingsystem.exceptions.BienInnexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import com.silverstone.houserentingsystem.utilisateur.UserRepository;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BienService {

    private final BienRepository bienRepository;
    private final UserRepository userRepository;
    private final AdresseRepo adresseRepo;
    private final CaracteristiqueRepo caracteristiqueRepo;

    public void enregistrerBien(BienDto bienDto) throws UtilisateurInexistantException {

        Optional<Utilisateur> utilisateurOptional = userRepository.findById(bienDto.getUserId());

        if (utilisateurOptional.isEmpty()){
            throw new UtilisateurInexistantException();
        }

        Optional<Adresse> adresseOptional = adresseRepo.findByRueAndCodepostalAndCommune(
                bienDto.getAdresse().getRue(),
                bienDto.getAdresse().getCodepostal(),
                bienDto.getAdresse().getCommune());

        Optional<Caracteristique> caracteristiqueOptional = caracteristiqueRepo.findByAllField(
                bienDto.getCaracteristique().getAnneeConstruction(),
                bienDto.getCaracteristique().isBalcon(),
                bienDto.getCaracteristique().isCave(),
                bienDto.getCaracteristique().isAscenseur(),
                bienDto.getCaracteristique().isGardien(),
                bienDto.getCaracteristique().getEnergie(),
                bienDto.getCaracteristique().getExposition()
        );

        Bien bien = new Bien(bienDto.getTypeBien(), bienDto.getSurface());

        if (adresseOptional.isEmpty()){
            Adresse adresse = new Adresse(bienDto.getAdresse().getNumRue(),
                    bienDto.getAdresse().getRue(),
                    bienDto.getAdresse().getCodepostal(),
                    bienDto.getAdresse().getCommune()
            );
            adresseRepo.save(adresse);
            bien.setAdresse(adresse);
        }else {
            bien.setAdresse(adresseOptional.get());
        }

        if (caracteristiqueOptional.isEmpty()){
            Caracteristique caracteristique = new Caracteristique(
                    bienDto.getCaracteristique().getAnneeConstruction(),
                    bienDto.getCaracteristique().isBalcon(),
                    bienDto.getCaracteristique().isCave(),
                    bienDto.getCaracteristique().isAscenseur(),
                    bienDto.getCaracteristique().isGardien(),
                    bienDto.getCaracteristique().getEnergie(),
                    bienDto.getCaracteristique().getExposition()
            );
            caracteristiqueRepo.save(caracteristique);
            bien.setCaracteristique(caracteristique);
        }else {
            bien.setCaracteristique(caracteristiqueOptional.get());
        }

        bien.setUtilisateur(utilisateurOptional.get());
        bienRepository.save(bien);
    }

    public List<Bien> afficherTousLesBiens(){
        return bienRepository.findAll();
    }

    public Bien afficherBienParId(Long bienId) throws BienInnexistantException {
        Optional<Bien> bienOptional = bienRepository.findById(bienId);
        if (bienOptional.isEmpty()){
            throw new BienInnexistantException();
        }
        return bienOptional.get();
    }

    public void supprimerBienParId(Long bienId) throws BienInnexistantException {
        Optional<Bien> bienOptional = bienRepository.findById(bienId);
        if (bienOptional.isEmpty()){
            throw new BienInnexistantException();
        }
        bienRepository.deleteById(bienId);
    }
}
