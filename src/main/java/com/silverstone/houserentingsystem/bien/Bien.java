package com.silverstone.houserentingsystem.bien;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silverstone.houserentingsystem.bien.adresse.Adresse;
import com.silverstone.houserentingsystem.bien.caracteristique.Caracteristique;
import com.silverstone.houserentingsystem.bien.enumeration.TypeBien;
import com.silverstone.houserentingsystem.offre.Offre;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Bien {

    @Id
    @SequenceGenerator(name = "bien_id", sequenceName = "bien_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bien_id")
    private Long bienId;

    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "adresseId")
    private Adresse adresse;

    private Long surface;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "caracteristique_id", referencedColumnName = "caracteristiqueId")
    private Caracteristique caracteristique;

    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore(value = true)
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "bienConcerne", cascade = CascadeType.ALL)
    private List<Offre> offres;

    public Bien(TypeBien typeBien, Adresse adresse, Long surface, Caracteristique caracteristique, Utilisateur utilisateur) {
        this.typeBien = typeBien;
        this.adresse = adresse;
        this.surface = surface;
        this.caracteristique = caracteristique;
        this.utilisateur = utilisateur;
    }

    public Bien(TypeBien typeBien, Long surface) {
        this.typeBien = typeBien;
        this.surface = surface;
    }
}
