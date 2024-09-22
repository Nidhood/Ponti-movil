package com.javeriana.pontimovil.ponti_movil.services;


import com.javeriana.pontimovil.ponti_movil.entities.Asignacion;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.entities.Direccion;
import com.javeriana.pontimovil.ponti_movil.exceptions.ConductorNotFoundException;
import com.javeriana.pontimovil.ponti_movil.exceptions.DireccionNotFoundException;
import com.javeriana.pontimovil.ponti_movil.repositories.AsignacionRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.ConductorRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.DireccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ConductorService {

    // Repositorios:
    ConductorRepository conductorRepository;
    DireccionRepository direccionRepository;
    AsignacionRepository asignacionRepository;

    // Constructor:
    @Autowired
    public ConductorService(ConductorRepository conductorRepository, DireccionRepository direccionRepository, AsignacionRepository asignacionRepository) {
        this.conductorRepository = conductorRepository;
        this.direccionRepository = direccionRepository;
        this.asignacionRepository = asignacionRepository;
    }

    // Métodos:
    public List<Conductor> obtenerConductores() {
        return conductorRepository.findAll();
    }

    public Conductor obtenerConductorPorId(UUID id) {
        return conductorRepository.findById(id).orElseThrow(()-> new ConductorNotFoundException(id));
    }

    public void crearConductor(Conductor conductor) {

        // Verificamos si la dirección ya existe:
        if(direccionRepository.findByTipoViaAndNumeroViaAndNumero(conductor.getDireccion().getTipoVia(), conductor.getDireccion().getNumeroVia(), conductor.getDireccion().getNumero()) == null){

            // Si la dirección no existe, la creamos:
            Direccion direccion = new Direccion(conductor.getDireccion().getTipoVia(), conductor.getDireccion().getNumeroVia(), conductor.getDireccion().getNumero(), conductor.getDireccion().getLocalidad(), conductor.getDireccion().getBarrio());
            direccionRepository.save(direccion);
        }

        // Si la dirección ya existe, la obtenemos:
        conductor.setDireccion(direccionRepository.findByTipoViaAndNumeroViaAndNumero(conductor.getDireccion().getTipoVia(), conductor.getDireccion().getNumeroVia(), conductor.getDireccion().getNumero()));
        conductorRepository.save(conductor);
    }

    public void actualizarConductor(UUID id, Conductor conductor) {
        Conductor conductorActual = conductorRepository.findById(id).orElseThrow(()-> new ConductorNotFoundException(id));
        Direccion direccion = direccionRepository.findById(conductor.getDireccion().getId()).orElseThrow(()-> new DireccionNotFoundException(conductor.getDireccion().getId()));

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
    }

    public void eliminarConductor(UUID id) {
        Conductor conductor = conductorRepository.findById(id).orElseThrow(()-> new ConductorNotFoundException(id));
        Direccion direccion = direccionRepository.findById(conductor.getDireccion().getId()).orElseThrow(()-> new DireccionNotFoundException(conductor.getDireccion().getId()));
        List<Asignacion> asignaciones = asignacionRepository.findByConductor(conductor);

        // Primero eliminamos todas las asignaciones del conductor:
        asignacionRepository.deleteAll(asignaciones);

        // Luego eliminamos el conductor:
        conductorRepository.delete(conductor);

        // Por último eliminamos la dirección:
        direccionRepository.delete(direccion);
    }
}
