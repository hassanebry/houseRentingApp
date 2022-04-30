package com.silverstone.houserentingsystem.offre;



import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class OffreDto {

    private final Long prix;
    private final String description;
    private final LocalDate disponibleLe;
    private final Long idBienConcerne;
    private final Long idUtilisateur;

}
