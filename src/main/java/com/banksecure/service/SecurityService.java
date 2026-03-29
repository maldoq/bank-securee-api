package com.banksecure.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.banksecure.repository.CompteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SecurityService {

    private final CompteRepository compteRepository;

    public boolean estProprietaire(Long compteId) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return compteRepository.findById(compteId)
                .map(compte -> compte.getProprietaire().getEmail().equals(email))
                .orElse(false);
    }
}
