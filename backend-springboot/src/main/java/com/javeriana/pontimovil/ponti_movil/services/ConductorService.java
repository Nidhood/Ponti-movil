package com.javeriana.pontimovil.ponti_movil.services;


import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado.cBusEnviandoDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado.cConductorEnviandoDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_recibiendo.cConductorRecibiendoDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_recibiendo.cDireccionRecibiendoDTO;
import com.javeriana.pontimovil.ponti_movil.entities.*;
import com.javeriana.pontimovil.ponti_movil.exceptions.BusNotFoundException;
import com.javeriana.pontimovil.ponti_movil.exceptions.ConductorNotFoundException;
import com.javeriana.pontimovil.ponti_movil.exceptions.DireccionNotFoundException;
import com.javeriana.pontimovil.ponti_movil.repositories.AsignacionRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.BusRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.ConductorRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ConductorService {

    private final BusRepository busRepository;
    // Repositorios:
    ConductorRepository conductorRepository;
    DireccionRepository direccionRepository;
    AsignacionRepository asignacionRepository;

    // Constructor:
    @Autowired
    public ConductorService(ConductorRepository conductorRepository, DireccionRepository direccionRepository, AsignacionRepository asignacionRepository, BusRepository busRepository) {
        this.conductorRepository = conductorRepository;
        this.direccionRepository = direccionRepository;
        this.asignacionRepository = asignacionRepository;
        this.busRepository = busRepository;
    }

    // Métodos:
    public List<cConductorRecibiendoDTO> listarConductores() {
        List<cConductorRecibiendoDTO> conductores = new ArrayList<>();
        List<cBusEnviandoDTO> buses = new ArrayList<>();

        // Obtenemos todos los conductores:
        List<Conductor> conductoresList = conductorRepository.findAll();

        // Crear DTO para cada conductor:
        for(Conductor conductor : conductoresList) {

            // Crear DTO:
            cConductorRecibiendoDTO conductorDTO = new cConductorRecibiendoDTO();
            conductorDTO.setId(conductor.getId());
            conductorDTO.setNombre(conductor.getNombre());
            conductorDTO.setApellido(conductor.getApellido());
            conductorDTO.setCedula(conductor.getCedula());
            conductorDTO.setTelefono(conductor.getTelefono());

            // Crear direccionDTO:
            cDireccionRecibiendoDTO direccionDTO = new cDireccionRecibiendoDTO();
            direccionDTO.setId(conductor.getDireccion().getId());
            direccionDTO.setTipoVia(conductor.getDireccion().getTipoVia());
            direccionDTO.setNumeroVia(conductor.getDireccion().getNumeroVia());
            direccionDTO.setNumero(conductor.getDireccion().getNumero());
            direccionDTO.setLocalidad(conductor.getDireccion().getLocalidad());
            direccionDTO.setBarrio(conductor.getDireccion().getBarrio());

            // Agregar a la lista:
            conductorDTO.setDireccion(direccionDTO);

            // obtenemos los días de la semana:
            List<String> dias = mapToDiasSemana(asignacionRepository.findByConductorId(conductor.getId()).stream().distinct().toList());

            // Crear DTO para cada bus:
            for (Bus bus : busRepository.findAll()) {

                // Crear DTO:
                cBusEnviandoDTO busDTO = new cBusEnviandoDTO();
                busDTO.setId(bus.getId());
                busDTO.setPlaca(bus.getPlaca());
                busDTO.setModelo(bus.getModelo());
                busDTO.setDentroConductor(asignacionRepository.existsByBusAndConductor(bus, conductor));

                // Agregar a la lista solo si esta dentro del conductor:
                if(busDTO.getDentroConductor()) {
                    buses.add(busDTO);
                }
            }

            // Creamos el DTO para el conductor:
            conductorDTO.setDiasSemana(dias);
            conductorDTO.setBuses(buses.stream().distinct().toList());

            // Agregar a la lista:
            conductores.add(conductorDTO);
        }

        // Retornar lista de conductores:
        return conductores;
    }

    public List<String> mapToDiasSemana(List<Asignacion> asignaciones) {

        // Mapeamos los conductores en el nuevo formato DTO:
        return ordenarDiasSemana(asignaciones.stream()
                .filter(Objects::nonNull) // Filtra los elementos nulos
                .map(Asignacion::getDiaSemana).toList());
    }

    private List<String> ordenarDiasSemana(List<String> diasSemana) {
        final List<String> ordenDias = Arrays.asList("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo");

        return diasSemana.stream()
                .sorted(Comparator.comparingInt(ordenDias::indexOf))
                .collect(Collectors.toList());
    }

    public List<Conductor> obtenerConductores() {
        return conductorRepository.findAll();
    }

    public Conductor obtenerConductorPorId(UUID id) {
        return conductorRepository.findById(id).orElseThrow(()-> new ConductorNotFoundException(id));
    }

    public List<cBusEnviandoDTO> obtenerBuses() {
        List<cBusEnviandoDTO> buses = new ArrayList<>();

        // Obtenemos todos los buses:
        List<Bus> busesList = busRepository.findAll();

        // Crear DTO para cada bus:
        for (Bus bus : busesList) {

            // Crear DTO:
            cBusEnviandoDTO busDTO = new cBusEnviandoDTO();
            busDTO.setId(bus.getId());
            busDTO.setPlaca(bus.getPlaca());
            busDTO.setModelo(bus.getModelo());
            busDTO.setDentroConductor(false);

            // Agregar a la lista:
            buses.add(busDTO);
        }

        // Retornar lista de buses:
        return buses;
    }

    public List<cBusEnviandoDTO> obtenerBusesPorConductor(UUID idConductor) {
        List<cBusEnviandoDTO> buses = new ArrayList<>();

        // Obtenemos el conductor:
        Conductor conductor = conductorRepository.findById(idConductor).orElseThrow(()-> new ConductorNotFoundException(idConductor));

        // Obtenemos todos los buses:
        List<Bus> busesList = busRepository.findAll();

        // Crear DTO para cada bus:
        for (Bus bus : busesList) {

            // Crear DTO:
            cBusEnviandoDTO busDTO = new cBusEnviandoDTO();
            busDTO.setId(bus.getId());
            busDTO.setPlaca(bus.getPlaca());
            busDTO.setModelo(bus.getModelo());
            busDTO.setDentroConductor(asignacionRepository.existsByBusAndConductor(bus, conductor));

            // Agregar a la lista:
            buses.add(busDTO);
        }

        // Retornar lista de buses:
        return buses;
    }

    @Transactional
    public void crearConductor(cConductorEnviandoDTO conductor) {
        Conductor conductorNuevo = new Conductor();

        // Creamos el conductor:
        conductorNuevo.setNombre(conductor.getNombre());
        conductorNuevo.setApellido(conductor.getApellido());
        conductorNuevo.setCedula(conductor.getCedula());
        conductorNuevo.setTelefono(conductor.getTelefono());

        // Verificamos si la dirección ya existe:
        if(direccionRepository.findByTipoViaAndNumeroViaAndNumero(conductor.getDireccion().getTipoVia(), conductor.getDireccion().getNumeroVia(), conductor.getDireccion().getNumero()) == null){

            // Si la dirección no existe, la creamos:
            Direccion direccion = new Direccion(conductor.getDireccion().getTipoVia(), conductor.getDireccion().getNumeroVia(), conductor.getDireccion().getNumero(), conductor.getDireccion().getLocalidad(), conductor.getDireccion().getBarrio());
            direccionRepository.save(direccion);
        }

        // Si la dirección ya existe, la obtenemos:
        conductorNuevo.setDireccion(direccionRepository.findByTipoViaAndNumeroViaAndNumero(conductor.getDireccion().getTipoVia(), conductor.getDireccion().getNumeroVia(), conductor.getDireccion().getNumero()));

        // Guardamos el conductor:
        conductorRepository.save(conductorNuevo);

        // Con base a los dias de la semana, creamos las asignaciones de conductores y buses:
        manejarAsignaciones(conductorNuevo, conductor.getBuses(), conductor.getDiasSemana());
    }

    @Transactional
    public void actualizarConductor(UUID id, cConductorEnviandoDTO conductor) {
        Conductor conductorActual = conductorRepository.findById(id).orElseThrow(() -> new ConductorNotFoundException(id));
        Direccion direccion = direccionRepository.findById(conductor.getDireccion().getId()).orElseThrow(() -> new DireccionNotFoundException(conductor.getDireccion().getId()));

        // Actualizamos los datos del conductor:
        conductorActual.setNombre(conductor.getNombre());
        conductorActual.setApellido(conductor.getApellido());
        conductorActual.setCedula(conductor.getCedula());
        conductorActual.setTelefono(conductor.getTelefono());

        // Actualizamos los datos de la dirección:
        direccion.setTipoVia(conductor.getDireccion().getTipoVia());
        direccion.setNumeroVia(conductor.getDireccion().getNumeroVia());
        direccion.setNumero(conductor.getDireccion().getNumero());
        direccion.setLocalidad(conductor.getDireccion().getLocalidad());
        direccion.setBarrio(conductor.getDireccion().getBarrio());

        // Guardamos los cambios:
        conductorRepository.save(conductorActual);
        direccionRepository.save(direccion);

        // Actualizamos las asignaciones de conductores:
        manejarAsignaciones(conductorActual, conductor.getBuses(), conductor.getDiasSemana());
    }

    @Transactional
    public void manejarAsignaciones(Conductor conductorExistente, List<cBusEnviandoDTO> buses, List<String> diasSemana) {
        List<String> todosDias = Arrays.asList("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo");

        // Manejamos las asignaciones para los buses en los días especificados
        for (String dia : diasSemana) {
            for (cBusEnviandoDTO bus : buses) {
                Bus busExistente = busRepository.findById(bus.getId()).orElseThrow(() -> new BusNotFoundException(bus.getId()));
                List<Asignacion> asignacionesExistentes = asignacionRepository.findByConductorIdAndBusIdAndDiaSemana(conductorExistente.getId(), busExistente.getId(), dia);

                if (asignacionesExistentes.isEmpty()) {
                    // Si no existe la asignación, la creamos
                    Asignacion nuevaAsignacion = new Asignacion();
                    nuevaAsignacion.setConductor(conductorExistente);
                    nuevaAsignacion.setBus(busExistente);
                    nuevaAsignacion.setDiaSemana(dia);
                    asignacionRepository.save(nuevaAsignacion);
                }
                // Si ya existe la asignación, no hacemos nada
            }
        }

        // Manejamos los días que no deben tener asignaciones
        List<String> diasAEliminar = new ArrayList<>(todosDias);
        diasAEliminar.removeAll(diasSemana);

        for (String dia : diasAEliminar) {
            List<Asignacion> asignacionesAEliminar = asignacionRepository.findByConductorIdAndDiaSemana(conductorExistente.getId(), dia);
            for (Asignacion asignacion : asignacionesAEliminar) {
                if (asignacion.getRuta() != null) {
                    // Si hay una ruta asignada, solo desvinculamos el conductor y el bus
                    asignacion.setConductor(null);
                    asignacion.setBus(null);
                    asignacionRepository.save(asignacion);
                } else {
                    // Si no hay ruta asignada, eliminamos la asignación
                    asignacionRepository.delete(asignacion);
                }
            }
        }

        // Eliminamos asignaciones de buses que ya no están en la lista
        List<UUID> busesIds = buses.stream().map(cBusEnviandoDTO::getId).collect(Collectors.toList());
        List<Asignacion> asignacionesAEliminar = asignacionRepository.findByConductorIdAndBusIdNotIn(conductorExistente.getId(), busesIds);
        for (Asignacion asignacion : asignacionesAEliminar) {
            if (asignacion.getRuta() != null) {
                // Si hay una ruta asignada, solo desvinculamos el conductor y el bus
                asignacion.setConductor(null);
                asignacion.setBus(null);
                asignacionRepository.save(asignacion);
            } else {
                // Si no hay ruta asignada, eliminamos la asignación
                asignacionRepository.delete(asignacion);
            }
        }
    }

    @Transactional
    public void eliminarConductor(UUID id) {
        Conductor conductor = conductorRepository.findById(id).orElseThrow(()-> new ConductorNotFoundException(id));
        Direccion direccion = direccionRepository.findById(conductor.getDireccion().getId()).orElseThrow(()-> new DireccionNotFoundException(conductor.getDireccion().getId()));
        List<Asignacion> asignaciones = asignacionRepository.findByConductor(conductor);

        // Primero eliminamos todas las asignaciones del conductor:
        for(Asignacion asignacion : asignaciones) {

            // Si no tiene asignaciones asociadas (solamente existe el conductor) eliminamos la asignación:
            if( asignacion.getBus() == null || asignacion.getRuta() == null) {
                asignacionRepository.delete(asignacion);
            }

            // Si tiene asignaciones asociadas (solo eliminamos el conductor dentro de cada asignación):
            else {
                asignacion.setConductor(null);
                asignacionRepository.save(asignacion);
            }
        }

        // Luego eliminamos el conductor:
        conductorRepository.delete(conductor);
    }
}
