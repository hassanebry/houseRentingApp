package com.silverstone.houserentingsystem.candidature;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CandidatureDto {
    private final String status;
    private final Long idUtilisateur;
    private final Long idOffre;
}
