package com.silverstone.houserentingsystem.utilisateur;

import com.silverstone.houserentingsystem.bien.Bien;
import com.silverstone.houserentingsystem.candidature.Candidature;
import com.silverstone.houserentingsystem.offre.Offre;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
public class Utilisateur {

    @Id
    @SequenceGenerator(name = "user_id", sequenceName = "user_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id")
    private Long userId;
    private String name;
    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilisateur")
    private List<Bien> bien;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilisateur")
    private List<Offre> offresDepose;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "utilisateur")
    private List<Candidature> candidatureDeposees;



    public Utilisateur(String name, String email, String password, String role) {
        this.name = name;
        this.email = email;
        this.password = password;

        switch (role) {
            case "owner" -> {
                this.role = Role.OWNER;
            }
            case "customer" -> {
                this.role = Role.CUSTOMER;
            }
            default -> {
                this.role = Role.ADMIN;
            }
        }
    }

}
