package com.silverstone.houserentingsystem.utilisateur;


import com.silverstone.houserentingsystem.exceptions.UtilisateurAlreadyExists;
import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<Utilisateur> getAllUsers(){
        return userRepository.findAll();
    }

    public Utilisateur getUserByEmail(String email) throws UtilisateurInexistantException {
        Optional<Utilisateur> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()){
            throw new UtilisateurInexistantException();
        }
        return optionalUser.get();
    }


    public Utilisateur saveUser(UserRequest userRequest) throws UtilisateurAlreadyExists {
        Optional<Utilisateur> optionalUtilisateur = userRepository.findByEmail(userRequest.getEmail());

        if (optionalUtilisateur.isPresent()){
            throw new UtilisateurAlreadyExists("this user already exist");
        }

        Utilisateur u = new Utilisateur(
                userRequest.getName(),
                userRequest.getEmail(),
                userRequest.getPassword(),
                userRequest.getRole());

        userRepository.save(u);
        return u;
    }

    public void deleteUserById(Long userId) throws UtilisateurInexistantException {
        if (userRepository.findById(userId).isEmpty()){
            throw new UtilisateurInexistantException();
        }
        userRepository.deleteById(userId);
    }
}
