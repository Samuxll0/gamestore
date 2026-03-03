package com.gamestore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gamestore.entity.Videojuego;
import com.gamestore.enums.Genero;

@Repository
public interface VideojuegoRepository 
        extends JpaRepository<Videojuego, Long> {

    // Derived Query
    List<Videojuego> findByGenero(Genero genero);

    // Derived Query (ignore case)
    List<Videojuego> findByTituloContainingIgnoreCase(String titulo);

    // JPQL
    @Query("SELECT v FROM Videojuego v WHERE v.precio BETWEEN :min AND :max")
    List<Videojuego> buscarPorRangoPrecio(
            @Param("min") Double min,
            @Param("max") Double max);

    // Nativa
    @Query(value = "SELECT * FROM videojuegos ORDER BY fecha_creacion DESC LIMIT 5",
           nativeQuery = true)
    List<Videojuego> ultimosCinco();
}
