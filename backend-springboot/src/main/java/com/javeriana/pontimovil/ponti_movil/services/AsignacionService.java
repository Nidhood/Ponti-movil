package com.javeriana.pontimovil.ponti_movil.services;

import com.javeriana.pontimovil.ponti_movil.entities.*;
import com.javeriana.pontimovil.ponti_movil.exceptions.*;
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
    AsignacionRepository asignacionRepository;
    EstacionRepository estacionRepository;
    RutaEstacionRepository rutaEstacionRepository;

    // Constructor:
    public AsignacionService(ConductorRepository conductorRepository, BusRepository busRepository, RutaRepository rutaRepository, AsignacionRepository asignacionRepository, EstacionRepository estacionRepository, RutaEstacionRepository rutaEstacionRepository) {
        this.conductorRepository = conductorRepository;
        this.busRepository = busRepository;
        this.rutaRepository = rutaRepository;
        this.asignacionRepository = asignacionRepository;
        this.estacionRepository = estacionRepository;
        this.rutaEstacionRepository = rutaEstacionRepository;
    }

    // Métodos:
    public List<Asignacion> obtenerAsignaciones() {
        return asignacionRepository.findAll();
    }

    public void asignarBus(UUID idConductor, UUID idBus, String diaSemana) {
        Conductor conductor = conductorRepository.findById(idConductor).orElseThrow(() -> new ConductorNotFoundException(idConductor));
        Bus bus = busRepository.findById(idBus).orElseThrow(() -> new BusNotFoundException(idBus));
        Asignacion asignacion = new Asignacion(conductor, bus, diaSemana);
        asignacionRepository.save(asignacion);
    }

    public void desasignarRuta(UUID idBus, UUID idRuta) {

        // Encontramos todas las asignaciones por bus y ruta todos los días de la semana:
        List<Asignacion> asignaciones = asignacionRepository.findByBusIdAndRutaId(idBus, idRuta);

        // Eliminamos todas las asignaciones encontradas:
        asignacionRepository.deleteAll(asignaciones);
    }

    public void asignarEstacion(UUID idRuta, UUID idEstacion) {
        RutaEstacion asignacion = new RutaEstacion();
        asignacion.setRuta(rutaRepository.findById(idRuta).orElseThrow(() -> new RutaNotFoundException(idRuta)));
        asignacion.setEstacion(estacionRepository.findById(idEstacion).orElseThrow(() -> new EstacionNotFoundException(idEstacion)));
        rutaEstacionRepository.save(asignacion);
    }

    public void desasignarEstacion(UUID idRuta, UUID idEstacion) {
        RutaEstacion asignacion = rutaEstacionRepository.findByRutaIdAndEstacionId(idRuta, idEstacion);
        rutaEstacionRepository.delete(asignacion);
    }

    public List<Asignacion> obtenerAsignacionesPorConductor(UUID idConductor) {
        return asignacionRepository.findByConductorId(idConductor);
    }

    public List<String> obtenerDiasDisponibles(UUID idBus) {
        return asignacionRepository.findDiasSemanaDisponibleByBusId(idBus);
    }
}
