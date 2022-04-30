package com.silverstone.houserentingsystem.candidature;

import com.silverstone.houserentingsystem.exceptions.AucuneCandidatureException;
import com.silverstone.houserentingsystem.exceptions.OffreInexistantException;
import com.silverstone.houserentingsystem.exceptions.StatusInexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/house-renting/candidature")
@AllArgsConstructor
public class CandidatureController {

    private final CandidatureServiceImpl candidatureService;

    @GetMapping
    public ResponseEntity<List<Candidature>> afficherToutesLesCandidatures(){
        return ResponseEntity.ok(candidatureService.listeToutesLesCandidatures());
    }

    @PostMapping
    public ResponseEntity<Candidature> deposerUneCandidature(@RequestBody CandidatureDto candidatureDto, UriComponentsBuilder builder){
        try {
            Candidature candidature = candidatureService.deposerUneCandidature(candidatureDto);
            return ResponseEntity.created(
                    URI.create(builder.toUriString()
                            +"/"+candidatureDto.getIdOffre()
                            +"/"+candidatureDto.getIdUtilisateur())).body(candidature);
        } catch (StatusInexistantException e) {
            return ResponseEntity.badRequest().build();
        } catch (UtilisateurInexistantException | OffreInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/user/{idUser}")
    public ResponseEntity<List<Candidature>> candidatureParUser(@PathVariable("idUser") Long idUser){
        try {
            List<Candidature> candidatures = candidatureService.candidatureParUtilisateur(idUser);
            return ResponseEntity.ok(candidatures);
        } catch (AucuneCandidatureException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/{idCandidature}")
    public ResponseEntity<Candidature> candidatureParIdCandidature(@PathVariable("idCandidature") Long idCandidature){
        try {
            Candidature candidature = candidatureService.candidatureParIdCandidature(idCandidature);
            return ResponseEntity.ok(candidature);
        } catch (AucuneCandidatureException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{idCandidature}")
    public ResponseEntity<Boolean> supprimerUneCandidature(@PathVariable("idCandidature") Long idCandidature){
        try {
            Boolean state = candidatureService.supprimerUneCandidature(idCandidature);
            return ResponseEntity.ok(state);
        } catch (AucuneCandidatureException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
