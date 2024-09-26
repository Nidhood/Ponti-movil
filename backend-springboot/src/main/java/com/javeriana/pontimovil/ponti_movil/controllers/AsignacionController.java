package com.javeriana.pontimovil.ponti_movil.controllers;

import com.javeriana.pontimovil.ponti_movil.entities.Asignacion;
import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.services.AsignacionService;
import com.javeriana.pontimovil.ponti_movil.services.BusService;
import com.javeriana.pontimovil.ponti_movil.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/asignaciones")
public class AsignacionController {

    // Repositorios:
    private final AsignacionService asignacionService;
    private final ConductorService conductorService;
    private final BusService busService;


    // Constructor:
    @Autowired
    public AsignacionController(AsignacionService asignacionService, ConductorService conductorService, BusService busService) {
        this.asignacionService = asignacionService;
        this.conductorService = conductorService;
        this.busService = busService;
    }

    // Métodos:
    @GetMapping
    public List<Asignacion> obtenerAsignaciones() {
        return asignacionService.obtenerAsignaciones();
    }

    // SOLO PAGINA
    @GetMapping("/{idConductor}/editar")
    public ModelAndView obtenerAsignacionesPorConductor(@PathVariable UUID idConductor) {
        Conductor conductor = conductorService.obtenerConductorPorId(idConductor);
        List<Asignacion> asignaciones = asignacionService.obtenerAsignacionesPorConductor(idConductor);
        List<Bus> buses = busService.obtenerBuses();

        // !!! Mejorar el codigo de abajo, que sea una Query !!!.
        // Crear un mapa de días disponibles para cada bus
        Map<UUID, List<String>> diasDisponiblesPorBus = new HashMap<>();
        buses.removeIf(bus -> {
            List<String> diasDisponibles = asignacionService.obtenerDiasDisponibles(bus.getId());
            if (diasDisponibles.isEmpty()) {
                return true; // Remueve el bus si no tiene días disponibles
            } else {
                diasDisponiblesPorBus.put(bus.getId(), diasDisponibles);
                return false;
            }
        });
        ModelAndView modelAndView = new ModelAndView("coordinator/c-gestionar-asignaciones-buses");
        modelAndView.addObject("asignaciones", asignaciones);
        modelAndView.addObject("buses", buses);
        modelAndView.addObject("conductor", conductor);
        modelAndView.addObject("diasDisponiblesPorBus", diasDisponiblesPorBus);
        return modelAndView;
    }

    @PostMapping("/{idConductor}/asignarBus/{idBus}")
    public void asignarBus(@PathVariable UUID idConductor, @PathVariable UUID idBus, @RequestParam List<String> diasSemana) {
        for (String diaSemana : diasSemana) {
            asignacionService.asignarBus(idConductor, idBus, diaSemana);
        }
    }

    @PostMapping("/{idConductor}/desasignarBus/{idBus}/{diaSemana}")
    public void desasignarBus(@PathVariable UUID idConductor, @PathVariable UUID idBus, @PathVariable String diaSemana) {
        asignacionService.desasignarBus(idConductor, idBus, diaSemana);
    }

    @PostMapping("/{idBus}/asignarRuta/{idRuta}")
    public void asignarRuta(@PathVariable UUID idBus, @PathVariable UUID idRuta, @RequestParam List<String> diasSemana) {
        for(String diaSemana : diasSemana) {
            asignacionService.asignarRuta(idBus, idRuta, diaSemana);
        }
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
