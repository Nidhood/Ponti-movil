package com.javeriana.pontimovil.ponti_movil.controllers;

import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado.cBusEnviandoDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado.cConductorEnviandoDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_recibiendo.cConductorRecibiendoDTO;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.services.ConductorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/conductores")
public class ConductorController {

    // Service:
    private final ConductorService conductorService;

    // Constructor:
    @Autowired
    public ConductorController(ConductorService conductorService) {
        this.conductorService = conductorService;
    }

    // Métodos:
    @Secured({ "Coordinador" })
    @GetMapping
    public List<cConductorRecibiendoDTO> listarConductores() {
        return conductorService.listarConductores();
    }

    @Secured({ "Coordinador" })
    @GetMapping("/buses")
    public List<cBusEnviandoDTO> obtenerBuses() {
        return conductorService.obtenerBuses();
    }

    @Secured({ "Coordinador" })
    @GetMapping("/{id}/buses")
    public List<cBusEnviandoDTO> obtenerBusesPorConductor(@PathVariable UUID id) {
        return conductorService.obtenerBusesPorConductor(id);
    }

    @Secured({ "Coordinador" })
    @PostMapping("/crear")
    public void crearConductor(@RequestBody cConductorEnviandoDTO conductor) {
        conductorService.crearConductor(conductor);
    }

    @Secured({ "Coordinador" })
    @PostMapping("/{id}/actualizar")
    public void actualizarConductor(@PathVariable UUID id, @RequestBody cConductorEnviandoDTO conductor) {
        conductorService.actualizarConductor(id, conductor);
    }

    @Secured({ "Coordinador" })
    @DeleteMapping("/{id}/eliminar")
    public void eliminarConductor(@PathVariable UUID id) {
        conductorService.eliminarConductor(id);
    }
}
