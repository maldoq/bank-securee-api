package com.banksecure.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banksecure.dto.AuthResponse;
import com.banksecure.dto.LoginRequest;
import com.banksecure.dto.RegisterRequest;
import com.banksecure.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;



@RestController // Sert à indiquer que cette classe est un contrôleur REST
@RequestMapping("/api/auth") // Définit le chemin de base pour les endpoints de ce contrôleur
@RequiredArgsConstructor // Génère un constructeur avec tous les champs finaux (final) de la classe
public class AuthController {
    private final AuthService authService; // Injection de dépendance pour le service d'authentification

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        //TODO: process POST request
        
        return ResponseEntity.ok(authService.register(request)); // Appelle le service d'authentification pour enregistrer l'utilisateur et retourne la réponse avec le token
    }

    @PostMapping("/register-admin")
    public ResponseEntity<AuthResponse> registerAdmin(@RequestBody RegisterRequest request) {
        //TODO: process POST request
        
        return ResponseEntity.ok(authService.registerAdmin(request)); // Appelle le service d'authentification pour enregistrer l'utilisateur et retourne la réponse avec le token
    }

    @PostMapping("/register-auditeur")
    public ResponseEntity<AuthResponse> registerAuditor(@RequestBody RegisterRequest request) {
        //TODO: process POST request
        
        return ResponseEntity.ok(authService.registerAuditor(request)); // Appelle le service d'authentification pour enregistrer l'utilisateur et retourne la réponse avec le token
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request, HttpServletRequest httpRequest) {
        //TODO: process POST request
        
        return ResponseEntity.ok(authService.login(request, httpRequest.getRemoteAddr())); // Appelle le service d'authentification pour connecter l'utilisateur et retourne la réponse avec le token
    }
    
    
}
