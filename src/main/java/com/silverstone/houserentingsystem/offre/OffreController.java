package com.silverstone.houserentingsystem.offre;

import com.silverstone.houserentingsystem.exceptions.BienInnexistantException;
import com.silverstone.houserentingsystem.exceptions.OffreInexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/house-renting/offre")
public class OffreController {
    private final OffreServiceImpl offreService;

    @PostMapping
    public ResponseEntity<Offre> createOffre(@RequestBody OffreDto offreDto, UriComponentsBuilder builder){
        try {
            Offre offre = offreService.deposerUneOffre(offreDto);
            return ResponseEntity.created(URI.create(builder.toUriString()+offre.getIdOffre())).body(offre);
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        } catch (BienInnexistantException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }

    @GetMapping
    public ResponseEntity<Collection<Offre>> afficherToutesLesOffres(){
        return ResponseEntity.ok(offreService.afficherToutesLesOffres());
    }

    @GetMapping(path = "/{idOffre}")
    public ResponseEntity<Offre> afficheOffreParId(@PathVariable("idOffre") Long idOffre){
        try {
            return ResponseEntity.ok(offreService.chercherUneOffreParId(idOffre));
        } catch (OffreInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idOffre}")
    public ResponseEntity<String> supprimerOffreParId(@PathVariable("idOffre") Long idOffre){
        try {
            offreService.supprimerUneOffre(idOffre);
            return ResponseEntity.status(204).body("l'offre est supprimee");
        } catch (OffreInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
