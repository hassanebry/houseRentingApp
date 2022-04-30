package com.silverstone.houserentingsystem.utilisateur;

import com.silverstone.houserentingsystem.exceptions.UtilisateurAlreadyExists;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/house-renting/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping
    public ResponseEntity<Utilisateur> createUser(@RequestBody UserRequest userRequest, UriComponentsBuilder builder){
        try {
            Utilisateur utilisateur = userService.saveUser(userRequest);
            return ResponseEntity.created(URI.create(builder.toUriString() + UUID.randomUUID())).body(utilisateur);
        } catch (UtilisateurAlreadyExists e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<Utilisateur> getuserByEmail(@PathVariable("email") String email){
        try {
            Utilisateur user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<String> deleteUserById(@PathVariable("userId") Long userId){
        try {
            userService.deleteUserById(userId);
            return ResponseEntity.status(204).body("User deleted !");
        } catch (UtilisateurInexistantException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
