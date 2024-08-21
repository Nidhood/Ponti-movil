package com.javeriana.pontimovil.ponti_movil.controllers;

import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import com.javeriana.pontimovil.ponti_movil.services.AsignacionBusRutaService;
import com.javeriana.pontimovil.ponti_movil.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/buses")
public class BusController {

    // Servicios:
    private final BusService busService;
    private final AsignacionBusRutaService asignacionBusRutaService;

    // Constructor:
    @Autowired
    public BusController(BusService busService, AsignacionBusRutaService asignacionBusRutaService) {
        this.busService = busService;
        this.asignacionBusRutaService = asignacionBusRutaService;
    }

    // Métodos:
    @GetMapping
    public List<Bus> obtenerBuses() {

        // Implementación
        return null;
    }

    @GetMapping("/{id}")
    public Bus obtenerBusPorId(@PathVariable UUID id, @RequestBody Bus bus) {

        // Implementación
        return null;
    }

    @PostMapping("/crear")
    public void crearBus(@RequestBody Bus bus) {

        // Implementación
    }

    @PostMapping("/{id}/actualizar")
    public void actualizarBus(@PathVariable UUID id, @RequestBody Bus bus) {

        // Implementación
    }

    @DeleteMapping("/{id}/eliminar")
    public void eliminarBus(@PathVariable UUID id) {

        // Implementación
    }

    @PostMapping("/{id}/asignarRuta")
    public void asignarRuta(@PathVariable UUID id, @RequestBody Bus bus) {

        // Implementación
    }

    @PostMapping("/{id}/desasignarRuta")
    public void desasignarRuta(@PathVariable UUID id, @RequestBody Bus bus) {

        // Implementación
    }
}
