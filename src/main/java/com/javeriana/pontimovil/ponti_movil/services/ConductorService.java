package com.javeriana.pontimovil.ponti_movil.services;


import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.entities.Direccion;
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

    // Constructor:
    @Autowired
    public ConductorService(ConductorRepository conductorRepository, DireccionRepository direccionRepository) {
        this.conductorRepository = conductorRepository;
        this.direccionRepository = direccionRepository;
    }


    // Métodos:
    public List<Conductor> obtenerConductores() {
        return conductorRepository.findAll();
    }

    public Conductor obtenerConductorPorId(UUID id) {
        return conductorRepository.findById(id).orElseThrow(()-> new RuntimeException("Conductor no encontrado"));
    }

    public void crearConductor(Conductor conductor) {
        Direccion direccion = new Direccion();
        if(direccionRepository.findByCalleAndCarreraAndNumero(conductor.getDireccion().getCalle(), conductor.getDireccion().getCarrera(), conductor.getDireccion().getNumero()) == null){
            direccion.setCalle(conductor.getDireccion().getCalle());
            direccion.setCarrera(conductor.getDireccion().getCarrera());
            direccion.setNumero(conductor.getDireccion().getNumero());
            direccion.setLocalidad(conductor.getDireccion().getLocalidad());
            direccion.setBarrio(conductor.getDireccion().getBarrio());
            direccionRepository.save(direccion);
        } else {
            direccion = direccionRepository.findByCalleAndCarreraAndNumero(conductor.getDireccion().getCalle(), conductor.getDireccion().getCarrera(), conductor.getDireccion().getNumero());
        }
        conductor.setDireccion(direccion);
        conductorRepository.save(conductor);
    }

    public void actualizarConductor(UUID id, Conductor conductor) {
        Conductor conductorActual = conductorRepository.findById(id).orElseThrow(()-> new RuntimeException("Conductor no encontrado"));
        Direccion direccion = direccionRepository.findById(conductor.getDireccion().getId()).orElseThrow(()-> new RuntimeException("Dirección no encontrada"));
        conductorActual.setNombre(conductor.getNombre());
        conductorActual.setApellido(conductor.getApellido());
        conductorActual.setCedula(conductor.getCedula());
        conductorActual.setTelefono(conductor.getTelefono());
        direccion.setCalle(conductor.getDireccion().getCalle());
        direccion.setCarrera(conductor.getDireccion().getCarrera());
        direccion.setNumero(conductor.getDireccion().getNumero());
        direccion.setLocalidad(conductor.getDireccion().getLocalidad());
        direccion.setBarrio(conductor.getDireccion().getBarrio());
        conductorRepository.save(conductorActual);
        direccionRepository.save(direccion);
    }

    public void eliminarConductor(UUID id) {
        Conductor conductor = conductorRepository.findById(id).orElseThrow(()-> new RuntimeException("Conductor no encontrado"));
        Direccion direccion = direccionRepository.findById(conductor.getDireccion().getId()).orElseThrow(()-> new RuntimeException("Dirección no encontrada"));
        conductorRepository.delete(conductor);
        direccionRepository.delete(direccion);
    }
}
