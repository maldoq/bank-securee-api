package com.banksecure.dto;

public class OperationRequest {
    private Long compteId;
    private String type; // DEPOT, RETRAIT, VIREMENT
    private double montant;
    private Long compteDestinationId; // pour virement
}
