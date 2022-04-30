package com.silverstone.houserentingsystem.utilisateur;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class UserRequest {
    private String name;
    private String email;
    private String password;
    private String role;
}
