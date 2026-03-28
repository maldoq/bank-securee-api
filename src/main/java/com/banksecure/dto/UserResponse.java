package com.banksecure.dto;

import com.banksecure.model.Role;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private Role role;
    private Boolean actif;
}
