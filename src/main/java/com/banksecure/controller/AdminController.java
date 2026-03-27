package com.banksecure.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin") // Définit le chemin de base pour les endpoints de ce contrôleur
@RequiredArgsConstructor // Génère un constructeur avec tous les champs finaux (final) de la classe
public class AdminController {
    
}
