package com.banksecure.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;

import com.banksecure.dto.admin.CompteResponse;
import com.banksecure.dto.admin.CreateCompteRequest;
import com.banksecure.dto.admin.UpdateCompteRequest;
import com.banksecure.model.Compte;
import com.banksecure.model.Utilisateur;
import com.banksecure.repository.CompteRepository;
import com.banksecure.repository.UtilisateurRepository;

@Service
@RequiredArgsConstructor
public class CompteService {
    private final CompteRepository compteRepository;
    private final UtilisateurRepository utilisateurRepository;

    private CompteResponse mapToResponse(Compte compte) {
        CompteResponse res = new CompteResponse();
        res.setId(compte.getId());
        res.setIban(compte.getIban());
        res.setSolde(compte.getSolde());
        res.setActif(compte.getActif());
        res.setUtilisateurId(compte.getProprietaire().getId());
        res.setEmailUtilisateur(compte.getProprietaire().getEmail());
        return res;
    }

    // Seul l'admin peut voir tous les comptes
    @PreAuthorize("hasRole('ADMIN')")
    public List<CompteResponse> getTousLesComptes(){
        return compteRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    // Admin ou propriétaire du compte
    @PreAuthorize("hasRole('ADMIN') or @securityService.estProprietaire(#id)")
    public CompteResponse getCompteParId(Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));
        return mapToResponse(compte);
    }

    // Seul l'admin peut créer un compte pour un utilisateur
    @PreAuthorize("hasRole('ADMIN')")
    public CompteResponse create(CreateCompteRequest request) {

        Utilisateur user = utilisateurRepository.findById(request.getUtilisateurId())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Compte compte = new Compte();
        compte.setIban(request.getIban());
        compte.setSolde(request.getSolde());
        compte.setProprietaire(user);

        return mapToResponse(compteRepository.save(compte));
    }

    // Seul l'admin peut mettre à jour un compte
    @PreAuthorize("hasRole('ADMIN')")
    public CompteResponse update(Long id, UpdateCompteRequest request) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));

        if (request.getIban() != null) {
            compte.setIban(request.getIban());
        }

        if (request.getActif() != null) {
            compte.setActif(request.getActif());
        }

        return mapToResponse(compteRepository.save(compte));
    }

    // Seul l'admin peut supprimer un compte (ou le désactiver)
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte introuvable"));

        compte.setActif(false);
        compteRepository.save(compte);
    }

    // Partie Client

    // Un client ne voit que ses propres comptes
    @PreAuthorize("hasRole('CLIENT') and #email == authentication.name")
    public List<Compte> getComptesParClient(String email){
        return compteRepository.findByProprietaireEmail(email);
    }

}
