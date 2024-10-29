package com.javeriana.pontimovil.ponti_movil.controllers;

import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_envio.bRutaEnvioDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_recibir.bBusRecibirDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_recibir.bConductorRecibirDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_recibir.bRutaRecibirDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.ruta_recibida.rBusDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado.cBusEnviandoDTO;
import com.javeriana.pontimovil.ponti_movil.entities.Asignacion;
import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.services.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
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
    public List<bBusRecibirDTO> obtenerBuses() {
        return busService.obtenerBuses();
    }

    @GetMapping("/rutas")
    public List<bRutaEnvioDTO> obtenerRutas(){
        return busService.obtenerRutas();
    }

    @GetMapping("/{id}/rutas")
    public List<bRutaEnvioDTO> obtenerRutasPorBus(@PathVariable UUID id) {
        return busService.obtenerRutasPorBus(id);
    }

    @PostMapping("/crear")
    public void crearBus(@RequestBody bBusRecibirDTO bus) {
        busService.crearBus(bus);
    }

    @PostMapping("/{id}/actualizar")
    public void actualizarBus(@PathVariable UUID id, @RequestBody bBusRecibirDTO bus) {
        busService.actualizarBus(id, bus);
    }

    @DeleteMapping("/{id}/eliminar")
    public void eliminarBus(@PathVariable UUID id) {
        busService.eliminarBus(id);
    }

}
