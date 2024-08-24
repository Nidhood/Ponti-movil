package com.javeriana.pontimovil.ponti_movil.controllers;

import com.javeriana.pontimovil.ponti_movil.entities.Bus;
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

    // Constructor:
    @Autowired
    public BusController(BusService busService) {
        this.busService = busService;
    }

    // Métodos:
    @GetMapping
    public List<Bus> obtenerBuses() {
        return busService.obtenerBuses();
    }

    @GetMapping("/{id}")
    public Bus obtenerBusPorId(@PathVariable UUID id) {
        return busService.obtenerBusPorId(id);
    }

    @PostMapping("/crear")
    public void crearBus(@RequestBody Bus bus) {
        busService.crearBus(bus);
    }

    @PostMapping("/{id}/actualizar")
    public void actualizarBus(@PathVariable UUID id, @RequestBody Bus bus) {
        busService.actualizarBus(id, bus);
    }

    @DeleteMapping("/{id}/eliminar")
    public void eliminarBus(@PathVariable UUID id) {
        busService.eliminarBus(id);
    }
}
