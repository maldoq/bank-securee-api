package com.banksecure.dto.admin;

import com.banksecure.model.Role;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String motDePasse;
    private Role role;
}
