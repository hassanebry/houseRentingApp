package com.silverstone.houserentingsystem.bien.caracteristique;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silverstone.houserentingsystem.bien.Bien;
import com.silverstone.houserentingsystem.bien.enumeration.Energie;
import com.silverstone.houserentingsystem.bien.enumeration.Exposition;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Caracteristique {

    @Id
    @SequenceGenerator(name = "caracteristique_id", sequenceName = "caracteristique_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "caracteristique_id")
    private Long caracteristiqueId;

    private int anneeConstruction;
    private boolean balcon;
    private boolean cave;
    private boolean ascenseur;
    private boolean gardien;

    @Enumerated(EnumType.STRING)
    private Energie energie;

    @Enumerated(EnumType.STRING)
    private Exposition exposition;

    @OneToOne(mappedBy = "caracteristique")
    @JsonIgnore(value = true)
    private Bien bien;

    public Caracteristique(int anneeConstruction, boolean balcon, boolean cave, boolean ascenseur, boolean gardien, Energie energie, Exposition exposition) {
        this.anneeConstruction = anneeConstruction;
        this.balcon = balcon;
        this.cave = cave;
        this.ascenseur = ascenseur;
        this.gardien = gardien;
        this.energie = energie;
        this.exposition = exposition;
    }
}
