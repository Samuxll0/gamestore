package com.gamestore.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gamestore.entity.Videojuego;
import com.gamestore.enums.Genero;
import com.gamestore.services.VideojuegoService;

@RestController
@RequestMapping("/api/videojuegos")
public class VideojuegoController {

    private final VideojuegoService service;

    public VideojuegoController(VideojuegoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Videojuego> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public Videojuego obtener(@PathVariable Long id) {
        return service.obtenerPorId(id);
    }

    @PostMapping
    public ResponseEntity<Videojuego> crear(
            @RequestBody Videojuego videojuego) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(service.crear(videojuego));
    }

    @PutMapping("/{id}")
    public Videojuego actualizar(@PathVariable Long id,
                                 @RequestBody Videojuego videojuego) {
        return service.actualizar(id, videojuego);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @GetMapping("/buscar")
    public List<Videojuego> buscar(@RequestParam String titulo) {
        return service.buscarPorTitulo(titulo);
    }

    @GetMapping("/genero")
    public List<Videojuego> buscarPorGenero(
            @RequestParam Genero genero) {

        return service.buscarPorGenero(genero);
    }

    @GetMapping("/rango-precio")
    public List<Videojuego> rango(
            @RequestParam Double min,
            @RequestParam Double max) {
        return service.buscarPorRango(min, max);
    }

    @PatchMapping("/{id}/descuento")
    public Videojuego descuento(@PathVariable Long id,
                                 @RequestParam Double porcentaje) {
        return service.aplicarDescuento(id, porcentaje);
    }
}
