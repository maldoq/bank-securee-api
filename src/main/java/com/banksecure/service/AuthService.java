package com.banksecure.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banksecure.dto.AuthResponse;
import com.banksecure.dto.LoginRequest;
import com.banksecure.dto.RegisterRequest;
import com.banksecure.model.Role;
import com.banksecure.model.Utilisateur;
import com.banksecure.repository.UtilisateurRepository;
import com.banksecure.security.filter.RateLimitFilter;
import com.banksecure.security.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RateLimitFilter rateLimitFilter;

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

    public AuthResponse login(LoginRequest request, String ip) {
         Utilisateur user = utilisateurRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    rateLimitFilter.enregistrerEchec(ip);
                    return new RuntimeException("Email ou mot de passe incorrect");
                });

        if (!passwordEncoder.matches(request.getMotDePasse(), user.getMotDePasse())) {
            rateLimitFilter.enregistrerEchec(ip);
            throw new RuntimeException("Email ou mot de passe incorrect");
        }

        if (!user.isActif()) {
            throw new RuntimeException("Compte désactivé");
        }

        // 🔥 succès → reset rate limit
        rateLimitFilter.reset(ip);

        String token = jwtService.genererToken(user);

        return new AuthResponse(token);

    }
}
