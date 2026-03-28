package com.banksecure.dto.admin;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CompteResponse {
    private Long id;
    private String iban;
    private BigDecimal solde;
    private boolean actif;
    private Long utilisateurId;
    private String emailUtilisateur;
}
