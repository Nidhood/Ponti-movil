package com.javeriana.pontimovil.ponti_movil.services;

import com.javeriana.pontimovil.ponti_movil.entities.AsignacionConductorBus;
import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.exceptions.NotFoundConductorException;
import com.javeriana.pontimovil.ponti_movil.repositories.AsignacionConductorBusRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.BusRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.ConductorRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AsignacionConductorBusService {

    // Repositorios:
    AsignacionConductorBusRepository asignacionConductorBusRepository;
    ConductorRepository conductorRepository;
    BusRepository busRepository;

    // Constructor:
    @Autowired
    public AsignacionConductorBusService(AsignacionConductorBusRepository asignacionConductorBusRepository, ConductorRepository conductorRepository, BusRepository busRepository) {
        this.asignacionConductorBusRepository = asignacionConductorBusRepository;
        this.conductorRepository = conductorRepository;
        this.busRepository = busRepository;
    }

    // Métodos:
    public void asignarBus(UUID id, UUID idBus, List<String> dia) {
        List<AsignacionConductorBus> asignacionesConductorBus = new ArrayList<>();
        for (String d : dia){
            if(asignacionConductorBusRepository.findAsignacionConductorBusByIdAndDiaSemana(id, d) != null){
                throw new RuntimeException("El conductor ya tiene asignado un bus para el día " + d);
            } else {
                asignacionesConductorBus.add(
                        new AsignacionConductorBus(
                                conductorRepository.findById(id).orElseThrow(()-> new NotFoundConductorException("Conductor no encontrado")),
                                busRepository.findById(idBus).orElseThrow(()-> new RuntimeException("Bus no encontrado")),
                                d
                        )
                );
            }
        }
        asignacionConductorBusRepository.saveAll(asignacionesConductorBus);
    }

    public void desasignarBus(UUID id, UUID idBus, List<String> dia) {
        List<AsignacionConductorBus> asignacionesConductorBus = new ArrayList<>();
        for (String d : dia){
            AsignacionConductorBus asignacionConductorBus = asignacionConductorBusRepository.findAsignacionConductorBusByIdAndDiaSemana(id, d);
            if(asignacionConductorBus == null){
                throw new RuntimeException("El conductor no tiene asignado un bus para el día " + d);
            } else {
                asignacionesConductorBus.add(asignacionConductorBus);
            }
        }
        asignacionConductorBusRepository.deleteAll(asignacionesConductorBus);
    }
}
