package com.banksecure.dto.admin;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class CreateCompteRequest {
    private String iban;
    private BigDecimal solde;
    private Long utilisateurId;
}
