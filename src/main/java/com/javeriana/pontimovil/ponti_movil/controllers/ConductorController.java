package com.javeriana.pontimovil.ponti_movil.controllers;

import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.services.AsignacionConductorBusService;
import com.javeriana.pontimovil.ponti_movil.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/conductores")
public class ConductorController {

    // Service:
    private final ConductorService conductorService;
    private final AsignacionConductorBusService asignacionConductorBusService;

    // Constructor:
    @Autowired
    public ConductorController(ConductorService conductorService, AsignacionConductorBusService asignacionConductorBusService) {
        this.conductorService = conductorService;
        this.asignacionConductorBusService = asignacionConductorBusService;
    }

    // Métodos:
    @GetMapping
    public List<Conductor> obtenerConductores() {

        // Implementación
        return null;
    }

    @GetMapping("/{id}")
    public Conductor obtenerConductorPorId(@PathVariable UUID id) {

        // Implementación
        return null;
    }

    @PostMapping("/crear")
    public void crearConductor(@RequestBody Conductor conductor) {

        // Implementación
    }

    @PostMapping("/{id}/actualizar")
    public void actualizarConductor(@PathVariable UUID id, @RequestBody Conductor conductor) {

        // Implementación
    }

    @DeleteMapping("/{id}/eliminar")
    public void eliminarConductor(@PathVariable UUID id) {

        // Implementación
    }

    @PostMapping("/{id}/asignarBus")
    public void asignarBus(@PathVariable UUID id, @RequestBody Conductor conductor) {

        // Implementación
    }

    @PostMapping("/{id}/desasignarBus")
    public void desasignarBus(@PathVariable UUID id, @RequestBody Conductor conductor) {

        // Implementación
    }
}
