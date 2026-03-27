package com.banksecure.repository;

import com.banksecure.model.Compte;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CompteRepository extends JpaRepository<Compte, Long> {
    List<Compte> findByProprietaireEmail(String email);

    Compte findById(long id);
}
