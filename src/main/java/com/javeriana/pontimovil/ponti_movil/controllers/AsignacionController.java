package com.javeriana.pontimovil.ponti_movil.controllers;


import com.javeriana.pontimovil.ponti_movil.entities.ConductorBusRuta;
import com.javeriana.pontimovil.ponti_movil.services.AsignacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/asignaciones")
public class AsignacionController {

    // Repositorios:
    private final AsignacionService asignacionService;


    // Constructor:
    @Autowired
    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    // Métodos:

    @GetMapping
    public List<ConductorBusRuta> obtenerAsignaciones() {
        return asignacionService.obtenerAsignaciones();
    }

    @GetMapping("/{id}")
    public ConductorBusRuta obtenerAsignacionPorId(@PathVariable UUID id) {
        return asignacionService.obtenerAsignacionPorId(id);
    }

    @PostMapping("/{idConductor}/asignarBus/{idBus}/{diaSemana}")
    public void asignarBus(@PathVariable UUID idConductor, @PathVariable UUID idBus, @PathVariable String diaSemana) {
        asignacionService.asignarBus(idConductor, idBus, diaSemana);
    }

    @PostMapping("/{idConductor}/desasignarBus/{idBus}")
    public void desasignarBus(@PathVariable UUID idConductor, @PathVariable UUID idBus) {
        asignacionService.desasignarBus(idConductor, idBus);
    }

    @PostMapping("/{idBus}/asignarRuta/{idRuta}")
    public void asignarRuta(@PathVariable UUID idBus, @PathVariable UUID idRuta) {
        asignacionService.asignarRuta(idBus, idRuta);
    }

    @PostMapping("/{idBus}/desasignarRuta/{idRuta}")
    public void desasignarRuta(@PathVariable UUID idBus, @PathVariable UUID idRuta) {
        asignacionService.desasignarRuta(idBus, idRuta);
    }

    @PostMapping("/{idRuta}/asignarEstacion/{idEstacion}")
    public void asignarEstacion(@PathVariable UUID idRuta, @PathVariable UUID idEstacion) {
        asignacionService.asignarEstacion(idRuta, idEstacion);
    }

    @PostMapping("/{idRuta}/desasignarEstacion/{idEstacion}")
    public void desasignarEstacion(@PathVariable UUID idRuta, @PathVariable UUID idEstacion) {
        asignacionService.desasignarEstacion(idRuta, idEstacion);
    }
}
