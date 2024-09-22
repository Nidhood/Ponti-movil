package com.javeriana.pontimovil.ponti_movil.services;


import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import com.javeriana.pontimovil.ponti_movil.exceptions.BusNotFoundException;
import com.javeriana.pontimovil.ponti_movil.repositories.BusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BusService {

    // Repositorio:
    private final BusRepository busRepository;

    // Constructor:
    @Autowired
    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    // Métodos:
    public List<Bus> obtenerBuses() {
        return busRepository.findAll();
    }

    public Bus obtenerBusPorId(UUID id) {
        return busRepository.findById(id).orElseThrow(()-> new BusNotFoundException(id));
    }

    public void crearBus(Bus bus) {
        busRepository.save(bus);
    }

    public void actualizarBus(UUID id, Bus bus) {
        Bus busActual = busRepository.findById(id).orElseThrow(()-> new BusNotFoundException(id));
        busActual.setPlaca(bus.getPlaca());
        busActual.setModelo(bus.getModelo());
        busRepository.save(busActual);
    }

    public void eliminarBus(UUID id) {
        busRepository.deleteById(id);
    }
}
