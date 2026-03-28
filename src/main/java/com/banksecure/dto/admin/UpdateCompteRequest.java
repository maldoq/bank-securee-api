package com.banksecure.dto.admin;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class UpdateCompteRequest {
    private String iban;
    private BigDecimal solde;
    private Boolean actif;
}
