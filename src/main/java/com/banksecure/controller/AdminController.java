package com.banksecure.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banksecure.dto.UserResponse;
import com.banksecure.dto.admin.CreateUserRequest;
import com.banksecure.dto.admin.UpdateUserRequest;
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
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers(); // Appelle le service pour récupérer tous les utilisateurs
    }

    // Détails d'un utilisateur
    @GetMapping("/utilisateurs/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        return adminService.getUserById(id);
    }

    // Ajouter un nouvel utilisateur
    @PostMapping("/utilisateurs")
    public UserResponse createUser(@RequestBody CreateUserRequest request) {
        return adminService.createUser(request);
    }
    
    // Mettre à jour un utilisateur
    @PutMapping("/utilisateurs/{id}")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return adminService.updateUser(id, request);
    }

    // Supprimer un utilisateur
    @DeleteMapping("/utilisateurs/{id}")
    public String deleteUser(@PathVariable Long id) {
        return adminService.deleteUser(id);
    }
    
}
