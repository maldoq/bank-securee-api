package com.banksecure.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banksecure.dto.admin.CreateUserRequest;
import com.banksecure.dto.admin.UpdateUserRequest;
import com.banksecure.model.Utilisateur;
import com.banksecure.repository.UtilisateurRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UtilisateurRepository utilisateurRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Utilisateur> getAllUsers (){
        return utilisateurRepository.findAll();
    }

    public Utilisateur getUserById(Long id){
        return utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
    }

    public Utilisateur createUser(CreateUserRequest request){
        if (utilisateurRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email déjà utilisé");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        utilisateur.setRole(request.getRole());

        return utilisateurRepository.save(utilisateur);
    }

    public Utilisateur updateUser(Long id, UpdateUserRequest request){
        Utilisateur utilisateur = getUserById(id);

        if (request.getEmail() != null){
            utilisateur.setEmail(request.getEmail());
        }

        if (request.getRole() != null){
            utilisateur.setRole(request.getRole());
        }

        if (request.getActif() != null){
            utilisateur.setActif(request.getActif());
        }

        return utilisateurRepository.save(utilisateur);
    }

    public String deleteUser(Long id){
        if (!utilisateurRepository.existsById(id)){
            throw new RuntimeException("Utilisateur non trouvé");
        }
        utilisateurRepository.deleteById(id);
        return "Utilisateur supprimé avec succès";
    }
}
