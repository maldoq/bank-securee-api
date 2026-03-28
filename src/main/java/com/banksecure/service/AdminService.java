package com.banksecure.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.banksecure.dto.UserResponse;
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

    private UserResponse mapToResponse(Utilisateur user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setActif(user.isActif());
        return response;
    }

    private Utilisateur getUserEntity(Long id) {
    return utilisateurRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
}

    public List<UserResponse> getAllUsers (){
        return utilisateurRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public UserResponse getUserById(Long id){
        return mapToResponse(utilisateurRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé")));
    }

    public UserResponse createUser(CreateUserRequest request){
        if (utilisateurRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email déjà utilisé");
        }

        Utilisateur utilisateur = new Utilisateur();
        utilisateur.setEmail(request.getEmail());
        utilisateur.setMotDePasse(passwordEncoder.encode(request.getMotDePasse()));
        utilisateur.setRole(request.getRole());

        return mapToResponse(utilisateurRepository.save(utilisateur));
    }

    public UserResponse updateUser(Long id, UpdateUserRequest request){
        Utilisateur utilisateur = getUserEntity(id);

        if (request.getEmail() != null){
            utilisateur.setEmail(request.getEmail());
        }

        if (request.getRole() != null){
            utilisateur.setRole(request.getRole());
        }

        if (request.getActif() != null){
            utilisateur.setActif(request.getActif());
        }

        return mapToResponse(utilisateurRepository.save(utilisateur));
    }

    public String deleteUser(Long id){
        if (!utilisateurRepository.existsById(id)){
            throw new RuntimeException("Utilisateur non trouvé");
        }
        utilisateurRepository.deleteById(id);
        return "Utilisateur supprimé avec succès";
    }
}
