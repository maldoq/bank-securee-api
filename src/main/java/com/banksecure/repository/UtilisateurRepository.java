package com.banksecure.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.banksecure.model.Utilisateur;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Optional<Utilisateur> findByEmail(String email);

    public boolean existsByEmail(String email);
    
}
