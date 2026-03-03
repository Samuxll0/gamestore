package com.gamestore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamestore.entity.Desarrolladora;
import com.gamestore.services.DesarrolladoraService;

@RestController
@RequestMapping("/api/desarrolladoras")
public class DesarrolladoraController {

    private final DesarrolladoraService service;

    public DesarrolladoraController(DesarrolladoraService service) {
        this.service = service;
    }

    @GetMapping
    public List<Desarrolladora> listar() {
        return service.listar();
    }

    @PostMapping
    public ResponseEntity<Desarrolladora> crear(
            @RequestBody Desarrolladora desarrolladora) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(desarrolladora));
    }
}
