package com.javeriana.pontimovil.ponti_movil.services;


import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.entities.ConductorBusRuta;
import com.javeriana.pontimovil.ponti_movil.entities.RutaEstacion;
import com.javeriana.pontimovil.ponti_movil.exceptions.AsignacionNotFoundException;
import com.javeriana.pontimovil.ponti_movil.exceptions.BusNotFoundException;
import com.javeriana.pontimovil.ponti_movil.exceptions.ConductorNotFoundException;
import com.javeriana.pontimovil.ponti_movil.exceptions.RutaNotFoundException;
import com.javeriana.pontimovil.ponti_movil.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AsignacionService {

    // Repositorios:
    ConductorRepository conductorRepository;
    BusRepository busRepository;
    RutaRepository rutaRepository;
    ConductorBusRutaRepository conductorBusRutaRepository;
    EstacionRepository estacionRepository;
    RutaEstacionRepository rutaEstacionRepository;

    // Constructor:
    public AsignacionService(ConductorRepository conductorRepository, BusRepository busRepository, RutaRepository rutaRepository, ConductorBusRutaRepository conductorBusRutaRepository, EstacionRepository estacionRepository, RutaEstacionRepository rutaEstacionRepository) {
        this.conductorRepository = conductorRepository;
        this.busRepository = busRepository;
        this.rutaRepository = rutaRepository;
        this.conductorBusRutaRepository = conductorBusRutaRepository;
        this.estacionRepository = estacionRepository;
        this.rutaEstacionRepository = rutaEstacionRepository;
    }

    // Métodos:
    public List<ConductorBusRuta> obtenerAsignaciones() {
        return conductorBusRutaRepository.findAll();
    }

    public ConductorBusRuta obtenerAsignacionPorId(UUID id) {
        return conductorBusRutaRepository.findById(id).orElseThrow(() -> new AsignacionNotFoundException(id));
    }

    public void asignarBus(UUID idConductor, UUID idBus, String diaSemana) {
        ConductorBusRuta asignacion = new ConductorBusRuta();
        Conductor conductor = conductorRepository.findById(idConductor).orElseThrow(() -> new ConductorNotFoundException(idConductor));
        Bus bus = busRepository.findById(idBus).orElseThrow(() -> new BusNotFoundException(idBus));
        asignacion.setConductor(conductor);
        asignacion.setBus(bus);
        asignacion.setDiaSemana(diaSemana);
        conductorBusRutaRepository.save(asignacion);
    }

    public void desasignarBus(UUID idConductor, UUID idBus) {
        ConductorBusRuta asignacion = conductorBusRutaRepository.findByConductorIdAndBusId(idConductor, idBus);
        conductorBusRutaRepository.delete(asignacion);
    }

    public void asignarRuta(UUID idBus, UUID idRuta) {
        ConductorBusRuta asignacion = new ConductorBusRuta();
        asignacion.setBus(busRepository.findById(idBus).orElseThrow(() -> new BusNotFoundException(idBus)));
        asignacion.setRuta(rutaRepository.findById(idRuta).orElseThrow(() -> new RutaNotFoundException(idRuta)));
        conductorBusRutaRepository.save(asignacion);
    }

    public void desasignarRuta(UUID idBus, UUID idRuta) {
        ConductorBusRuta asignacion = conductorBusRutaRepository.findByBusIdAndRutaId(idBus, idRuta);
        conductorBusRutaRepository.delete(asignacion);
    }

    public void asignarEstacion(UUID idRuta, UUID idEstacion) {
        RutaEstacion asignacion = new RutaEstacion();
        asignacion.setRuta(rutaRepository.findById(idRuta).orElseThrow(() -> new RutaNotFoundException(idRuta)));
        asignacion.setEstacion(estacionRepository.findById(idEstacion).orElseThrow(() -> new RutaNotFoundException(idEstacion)));
        rutaEstacionRepository.save(asignacion);
    }

    public void desasignarEstacion(UUID idRuta, UUID idEstacion) {
        RutaEstacion asignacion = rutaEstacionRepository.findByRutaIdAndEstacionId(idRuta, idEstacion);
        rutaEstacionRepository.delete(asignacion);
    }
}
