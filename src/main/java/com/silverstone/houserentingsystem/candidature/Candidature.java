package com.silverstone.houserentingsystem.candidature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silverstone.houserentingsystem.offre.Offre;
import com.silverstone.houserentingsystem.utilisateur.Utilisateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Candidature {
    @Id
    @SequenceGenerator(name = "candidature_id", sequenceName = "candidature_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "candidature_id")
    private Long idCandidature;

    private LocalDate dateCandidature;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JsonIgnore
    private Utilisateur utilisateur;

    @ManyToOne
    @JsonIgnore
    private Offre offre;

    public Candidature(Status status) {
        this.dateCandidature = LocalDate.now();
        this.status = status;
    }
}
