package com.banksecure.dto.admin;

import com.banksecure.model.Role;

import lombok.Data;

@Data // Génère les getters, setters, toString, equals et hashCode pour cette classe
public class UpdateUserRequest {
    private String email;
    private Role role;
    private Boolean actif;
}
