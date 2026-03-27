package com.banksecure.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banksecure.dto.AuthResponse;
import com.banksecure.dto.RegisterRequest;
import com.banksecure.dto.LoginRequest;
import com.banksecure.model.Role;
import com.banksecure.model.Utilisateur;
import com.banksecure.repository.UtilisateurRepository;
import com.banksecure.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthResponse register(RegisterRequest request){

        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Utilisateur user = new Utilisateur();
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        user.setRole(Role.CLIENT);
        utilisateurRepository.save(user);

        String token = jwtService.genererToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse registerAdmin(RegisterRequest request){

        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Utilisateur user = new Utilisateur();
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        user.setRole(Role.ADMIN);
        utilisateurRepository.save(user);

        String token = jwtService.genererToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse registerAuditor(RegisterRequest request){

        if (utilisateurRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Utilisateur user = new Utilisateur();
        user.setEmail(request.getEmail());
        user.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        user.setRole(Role.AUDITEUR);
        utilisateurRepository.save(user);

        String token = jwtService.genererToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        Utilisateur user = utilisateurRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        String token = jwtService.genererToken(user);

        return new AuthResponse(token);

    }
}
