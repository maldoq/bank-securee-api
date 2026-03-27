package com.banksecure.model;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity // Indique que cette classe est une entité JPA
@Data // Génère automatiquement les getters, setters, equals, hashCode et toString
@NoArgsConstructor // Génère un constructeur sans arguments
@AllArgsConstructor // Génère un constructeur avec tous les arguments
public class Utilisateur implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Génère automatiquement l'ID
    private Long id;

    @Column(unique = true, nullable = false) // L'email doit être unique et non nul
    private String email;

    @Column(nullable = false) // Le mot de passe ne peut pas être nul
    private String motDePasse; // Toujours haché en base !

    @Enumerated(EnumType.STRING) // Stocke l'énumération en tant que chaîne de caractères dans la base de données
    private Role role;

    private boolean actif = true;

    // 🔐 SPRING SECURITY

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name());
    }

    @Override
    public String getPassword() {
        return motDePasse;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return actif;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return actif;
    }
}
