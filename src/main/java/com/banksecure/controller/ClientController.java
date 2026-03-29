package com.banksecure.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.banksecure.dto.admin.CompteResponse;
import com.banksecure.service.CompteService;

import lombok.RequiredArgsConstructor;




@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {
    private final CompteService compteService;

    @GetMapping("/comptes")
    public List<CompteResponse> getMesComptes() {
        return compteService.getMesComptes();
    }

    @GetMapping("/comptes/{id}")
    public CompteResponse getMonCompteParId(@PathVariable Long id) {
        return compteService.getMonCompteParId(id);
    }
    
    
}
