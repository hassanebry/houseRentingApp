package com.silverstone.houserentingsystem.bien;

import com.silverstone.houserentingsystem.exceptions.BienInnexistantException;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/house-renting/bien")
@AllArgsConstructor
public class BienController {

    private final BienService bienService;

    @PostMapping
    public ResponseEntity<BienDto> enregistrerUnBien(@RequestBody BienDto bienDto,
                                                     UriComponentsBuilder builder){
        try {
            bienService.enregistrerBien(bienDto);
            return ResponseEntity.created(URI.create(builder.toUriString()+ UUID.randomUUID())).body(bienDto);
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Bien>> afficherLesBiens(){
        List<Bien> biens = bienService.afficherTousLesBiens();
        return ResponseEntity.ok(biens);
    }

    @GetMapping(path = "/{bienId}")
    public ResponseEntity<Bien> afficherBienParId(@PathVariable("bienId") Long bienId){
        try {
            Bien bien = bienService.afficherBienParId(bienId);
            return ResponseEntity.ok(bien);
        } catch (BienInnexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{bienId}")
    public ResponseEntity<String> supprimerBienParId(@PathVariable("bienId") Long bienId){
        try {
            bienService.supprimerBienParId(bienId);
            return ResponseEntity.status(204).body("Bien supprimé !");
        } catch (BienInnexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
