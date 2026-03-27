package com.banksecure.service;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.banksecure.model.Utilisateur;
import com.banksecure.repository.UtilisateurRepository;

@Service // Indique que cette classe est un service Spring, géré par le conteneur de dépendances
@RequiredArgsConstructor // Génère un constructeur avec tous les arguments finaux (pour l'injection de dépendances)
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UtilisateurRepository utilisateurRepository; // Injection du repository pour accéder aux données des utilisateurs

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException{
        Utilisateur user = utilisateurRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé: " + email));

        return User.builder()
            .username(user.getEmail())
            .password(user.getMotDePasse())
            .roles(user.getRole().name())
            .accountLocked(!user.isActif())
            .build();
    }

}
