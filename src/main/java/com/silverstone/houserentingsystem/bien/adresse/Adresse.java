package com.silverstone.houserentingsystem.bien.adresse;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silverstone.houserentingsystem.bien.Bien;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class Adresse {

    @Id
    @SequenceGenerator(name = "adresse_id", sequenceName = "adresse_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adresse_id")
    private Long adresseId;
    private int numRue;
    private String rue;
    private int codepostal;
    private String commune;

    @OneToOne(mappedBy = "adresse")
    @JsonIgnore(value = true)
    private Bien bien;

    public Adresse(int numRue, String rue, int codepostal, String commune) {
        this.numRue = numRue;
        this.rue = rue;
        this.codepostal = codepostal;
        this.commune = commune;
    }
}
