package com.gamestore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gamestore.entity.Desarrolladora;
import com.gamestore.exception.ResourceNotFoundException;
import com.gamestore.repository.DesarrolladoraRepository;

@Service
public class DesarrolladoraService {

    private final DesarrolladoraRepository repository;

    public DesarrolladoraService(DesarrolladoraRepository repository) {
        this.repository = repository;
    }

    public List<Desarrolladora> listar() {
        return repository.findAll();
    }

    public Desarrolladora crear(Desarrolladora desarrolladora) {
        return repository.save(desarrolladora);
    }

    public Desarrolladora obtenerPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Desarrolladora no encontrada"));
    }
}
