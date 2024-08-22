package com.javeriana.pontimovil.ponti_movil.controllers;


import com.javeriana.pontimovil.ponti_movil.dto.NuevaRutaDto;
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
        return rutaService.obtenerRutas();
    }

    @GetMapping("/{id}")
    public Ruta obtenerRutaPorId(@PathVariable UUID id) {
        return rutaService.obtenerRutaPorId(id);
    }

    @PostMapping("/crear")
    public void crearRuta(@RequestBody NuevaRutaDto nuevaRutaDto) {
        rutaService.crearRuta(nuevaRutaDto);
    }

    @PostMapping("/{codigo}/actualizar")
    public void actualizarRuta(@PathVariable String codigo, @RequestBody NuevaRutaDto nuevaRutaDto) {
        rutaService.actualizarRuta(codigo, nuevaRutaDto);
    }

    @DeleteMapping("/{codigo}/eliminar")
    public void eliminarRuta(@PathVariable String codigo) {
        rutaService.eliminarRuta(codigo);
    }
}
