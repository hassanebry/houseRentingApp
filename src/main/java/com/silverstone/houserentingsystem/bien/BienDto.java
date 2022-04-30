package com.silverstone.houserentingsystem.bien;

import com.silverstone.houserentingsystem.bien.adresse.Adresse;
import com.silverstone.houserentingsystem.bien.caracteristique.Caracteristique;
import com.silverstone.houserentingsystem.bien.enumeration.TypeBien;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class BienDto {

    @Enumerated(EnumType.STRING)
    private TypeBien typeBien;

    private Adresse adresse;

    private Long surface;

    private Caracteristique caracteristique;

    private Long userId;

}
