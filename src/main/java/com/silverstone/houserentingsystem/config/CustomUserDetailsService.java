package com.silverstone.houserentingsystem.config;

import com.silverstone.houserentingsystem.exceptions.UtilisateurInexistantException;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import com.silverstone.houserentingsystem.utilisateur.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Utilisateur u = userService.getUserByEmail(username);
            UserDetails userDetails = User.builder()
                    .username(u.getEmail())
                    .password(passwordEncoder.encode(u.getPassword()))
                    .roles(String.valueOf(u.getRole()))
                    .build();
            return userDetails;
        } catch (UtilisateurInexistantException e) {
            throw new UsernameNotFoundException("username  " +username );
        }
    }
}
