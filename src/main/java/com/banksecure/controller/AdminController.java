package com.banksecure.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banksecure.model.Utilisateur;
import com.banksecure.service.AdminService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/admin") // Définit le chemin de base pour les endpoints de ce contrôleur
@RequiredArgsConstructor // Génère un constructeur avec tous les champs finaux (final) de la classe
@PreAuthorize("hasRole('ADMIN')") // Assure que seuls les utilisateurs avec le rôle ADMIN peuvent accéder aux endpoints de ce contrôleur
public class AdminController {
    private final AdminService adminService; // Injection de la dépendance du service AdminService

    // Liste des utilisateurs
    @GetMapping("/utilisateurs")
    public List<Utilisateur> getAllUsers() {
        return adminService.getAllUsers(); // Appelle le service pour récupérer tous les utilisateurs
    }
    
}
