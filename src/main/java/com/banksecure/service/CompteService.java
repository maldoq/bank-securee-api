package com.banksecure.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import com.banksecure.model.Compte;
import com.banksecure.repository.CompteRepository;

@Service
@RequiredArgsConstructor
public class CompteService {
    private final CompteRepository compteRepository;

    // Seul l'admin peut voir tous les comptes
    @PreAuthorize("hasRole('ADMIN')")
    public List<Compte> getTousLesComptes(){
        return compteRepository.findAll();
    }

    // Un client ne voit que ses propres comptes
    @PreAuthorize("hasRole('CLIENT') and #email == authentication.name")
    public List<Compte> getComptesParClient(String email){
        return compteRepository.findByProprietaireEmail(email);
    }

    // Admin ou propriétaire du compte
    @PreAuthorize("hasRole('ADMIN') or @securityService.estProprietaire(#id)")
    public Compte getCompteParId(long id){
        return compteRepository.findById(id);
    }

}
