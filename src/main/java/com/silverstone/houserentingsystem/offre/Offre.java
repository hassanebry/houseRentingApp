package com.silverstone.houserentingsystem.offre;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silverstone.houserentingsystem.bien.Bien;
import com.silverstone.houserentingsystem.candidature.Candidature;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class Offre {

    @Id
    @SequenceGenerator(name = "offre_id", sequenceName = "offre_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offre_id")
    private Long idOffre;

    private Long prix;
    private String description;
    private LocalDateTime deposeLe;
    private LocalDate expireLe;
    private LocalDate disponibleLe;
    @ManyToOne
    @JsonIgnore
    private Bien bienConcerne;

    @ManyToOne
    @JsonIgnore
    private Utilisateur utilisateur;

    @OneToMany(mappedBy = "offre", cascade = CascadeType.ALL)
    private List<Candidature> candidatures;

    public Offre(Long prix, String description, LocalDate disponibleLe) {
        this.prix = prix;
        this.description = description;
        this.deposeLe = LocalDateTime.now();
        this.expireLe = LocalDate.now().plusMonths(2);
        this.disponibleLe = disponibleLe;
    }
}
