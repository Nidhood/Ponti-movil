package com.javeriana.pontimovil.ponti_movil.controllers;

import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.init.DBInitializer;
import com.javeriana.pontimovil.ponti_movil.services.AsignacionConductorBusService;
import com.javeriana.pontimovil.ponti_movil.services.ConductorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    // Logs:
    private static final Logger logger = LoggerFactory.getLogger(ConductorController.class);

    // Constructor:
    @Autowired
    public ConductorController(ConductorService conductorService, AsignacionConductorBusService asignacionConductorBusService) {
        this.conductorService = conductorService;
        this.asignacionConductorBusService = asignacionConductorBusService;
    }

    // Métodos:
    @GetMapping
    public List<Conductor> obtenerConductores() {
        return conductorService.obtenerConductores();
    }

    @GetMapping("/{id}")
    public Conductor obtenerConductorPorId(@PathVariable UUID id) {
        return conductorService.obtenerConductorPorId(id);
    }

    @PostMapping("/crear")
    public void crearConductor(@RequestBody Conductor conductor) {
        // logger.info("Creando conductor: " + conductor);
        conductorService.crearConductor(conductor);
    }

    @PostMapping("/{id}/actualizar")
    public void actualizarConductor(@PathVariable UUID id, @RequestBody Conductor conductor) {
        conductorService.actualizarConductor(id, conductor);
    }

    @DeleteMapping("/{id}/eliminar")
    public void eliminarConductor(@PathVariable UUID id) {
        conductorService.eliminarConductor(id);
    }

    @PostMapping("/{id}/asignarBus")
    public void asignarBus(@PathVariable UUID id, @RequestBody Bus bus, @RequestBody List<String> dia) {
        asignacionConductorBusService.asignarBus(id, bus.getId(), dia);
    }

    @PostMapping("/{id}/desasignarBus")
    public void desasignarBus(@PathVariable UUID id, @RequestBody Bus bus, @RequestBody List<String> dia) {
        asignacionConductorBusService.desasignarBus(id, bus.getId(), dia);
    }
}
