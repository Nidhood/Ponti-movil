package com.javeriana.pontimovil.ponti_movil.controllers;


import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.services.RutaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/rutas")
public class RutaController {

    // Service:
    private final RutaService rutaService;

    // Constructor:
    public RutaController(RutaService rutaService) {
        this.rutaService = rutaService;
    }

    // Métodos:
    @GetMapping
    public List<Ruta> obtenerRutas() {

        // Implementación
        return null;
    }

    @GetMapping("/{id}")
    public Ruta obtenerRutaPorId(@PathVariable UUID id) {

        // Implementación
        return null;
    }

    @PostMapping("/crear")
    public void crearRuta(@RequestBody Ruta ruta) {

        // Implementación
    }

    @PostMapping("/{id}/actualizar")
    public void actualizarRuta(@PathVariable UUID id, @RequestBody Ruta ruta) {

        // Implementación
    }

    @DeleteMapping("/{id}/eliminar")
    public void eliminarRuta(@PathVariable UUID id) {

        // Implementación
    }
}
