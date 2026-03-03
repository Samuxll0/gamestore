package com.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gamestore.entity.Desarrolladora;

@Repository
public interface DesarrolladoraRepository extends JpaRepository<Desarrolladora, Long> {
    
}
