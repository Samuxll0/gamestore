package com.gamestore.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.gamestore.entity.Videojuego;
import com.gamestore.enums.Genero;
import com.gamestore.exception.ResourceNotFoundException;
import com.gamestore.repository.DesarrolladoraRepository;
import com.gamestore.repository.VideojuegoRepository;

@Service
public class VideojuegoService {

    private final VideojuegoRepository repository;
    private final DesarrolladoraRepository desarrolladoraRepository;

    public VideojuegoService(VideojuegoRepository repository,
                             DesarrolladoraRepository desarrolladoraRepository) {
        this.repository = repository;
        this.desarrolladoraRepository = desarrolladoraRepository;
    }

    private void validar(Videojuego videojuego) {

        if (videojuego.getPrecio() < 0)
            throw new IllegalArgumentException("El precio no puede ser negativo");

        if (videojuego.getTitulo() == null || videojuego.getTitulo().isBlank())
            throw new IllegalArgumentException("El título no puede estar vacío");

        if (!desarrolladoraRepository.existsById(
                videojuego.getDesarrolladora().getId()))
            throw new IllegalArgumentException("La desarrolladora no existe");
    }

    private void calcularIva(Videojuego v) {
        v.setPrecioConIva(v.getPrecio() * 1.19);
    }

    public List<Videojuego> listar() {
        List<Videojuego> lista = repository.findAll();
        lista.forEach(this::calcularIva);
        return lista;
    }

    public Videojuego obtenerPorId(Long id) {
        Videojuego v = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Videojuego no encontrado"));
        calcularIva(v);
        return v;
    }

    public Videojuego crear(Videojuego videojuego) {
        validar(videojuego);
        return repository.save(videojuego);
    }

    public Videojuego actualizar(Long id, Videojuego nuevo) {
        Videojuego existente = obtenerPorId(id);

        validar(nuevo);

        existente.setTitulo(nuevo.getTitulo());
        existente.setPrecio(nuevo.getPrecio());
        existente.setGenero(nuevo.getGenero());
        existente.setCodigoRegistro(nuevo.getCodigoRegistro());
        existente.setDesarrolladora(nuevo.getDesarrolladora());

        return repository.save(existente);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public List<Videojuego> buscarPorTitulo(String texto) {
        List<Videojuego> lista =
                repository.findByTituloContainingIgnoreCase(texto);
        lista.forEach(this::calcularIva);
        return lista;
    }

    public List<Videojuego> buscarPorGenero(Genero genero) {

        List<Videojuego> lista = repository.findByGenero(genero);

        lista.forEach(v -> 
            v.setPrecioConIva(v.getPrecio() * 1.19)
        );

        return lista;
    }

    public List<Videojuego> buscarPorRango(Double min, Double max) {
        List<Videojuego> lista =
                repository.buscarPorRangoPrecio(min, max);
        lista.forEach(this::calcularIva);
        return lista;
    }

    public Videojuego aplicarDescuento(Long id, Double porcentaje) {
        Videojuego v = obtenerPorId(id);

        double nuevoPrecio =
                v.getPrecio() - (v.getPrecio() * porcentaje / 100);

        v.setPrecio(nuevoPrecio);

        repository.save(v);

        calcularIva(v);

        return v;
    }
}
