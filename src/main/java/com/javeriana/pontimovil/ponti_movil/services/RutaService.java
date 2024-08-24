package com.javeriana.pontimovil.ponti_movil.services;

import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.entities.RutaEstacion;
import com.javeriana.pontimovil.ponti_movil.exceptions.HorarioNotFoundException;
import com.javeriana.pontimovil.ponti_movil.exceptions.RutaNotFoundException;
import com.javeriana.pontimovil.ponti_movil.repositories.ConductorBusRutaRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.HorarioRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaEstacionRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class RutaService {

    // Repositorio:
    RutaRepository rutaRepository;
    HorarioRepository horarioRepository;
    RutaEstacionRepository rutaEstacionRepository;
    ConductorBusRutaRepository conductorBusRutaRepository;

    // Constructor:
    @Autowired
    public RutaService(RutaRepository rutaRepository, HorarioRepository horarioRepository, RutaEstacionRepository rutaEstacionRepository, ConductorBusRutaRepository conductorBusRutaRepository) {
        this.rutaRepository = rutaRepository;
        this.horarioRepository = horarioRepository;
        this.rutaEstacionRepository = rutaEstacionRepository;
        this.conductorBusRutaRepository = conductorBusRutaRepository;
    }

    // Métodos:
    public List<Ruta> obtenerRutas() {
        return rutaRepository.findAll();
    }

    public List<RutaEstacion> obtenerEstacionesPorRuta(UUID id) {
        return rutaEstacionRepository.findByRuta(rutaRepository.findById(id).orElseThrow(()-> new RutaNotFoundException(id)));
    }

    public Ruta obtenerRutaPorId(UUID id) {
        return rutaRepository.findById(id).orElseThrow(()-> new RutaNotFoundException(id));
    }

    public void crearRuta(Ruta ruta) {

        // Buscamos si el horario ya existe, si no lo creamos:
        if (ruta.getHorario().getId() == null) {
            Horario horario = new Horario();
            horario.setDia(ruta.getHorario().getDia());
            horario.setHoraInicio(ruta.getHorario().getHoraInicio());
            horario.setHoraFin(ruta.getHorario().getHoraFin());
            horarioRepository.save(horario);

            // Asignamos el horario a la ruta:
            ruta.setHorario(horario);
        }
        rutaRepository.save(ruta);
    }

    public void actualizarRuta(UUID id, Ruta ruta) {
        Ruta rutaExistente = rutaRepository.findById(id).orElseThrow(()-> new RutaNotFoundException(id));
        Horario horarioExistente = rutaExistente.getHorario();

        // Actualizamos el horario existente:
        horarioExistente.setDia(ruta.getHorario().getDia());
        horarioExistente.setHoraInicio(ruta.getHorario().getHoraInicio());
        horarioExistente.setHoraFin(ruta.getHorario().getHoraFin());
        horarioRepository.save(horarioExistente);

        // Actualizamos los campos de la ruta existente:
        rutaExistente.setCodigo(ruta.getCodigo());
        rutaRepository.save(rutaExistente);
    }

    public void eliminarRuta(UUID id) {
        Ruta ruta = rutaRepository.findById(id).orElseThrow(()-> new RutaNotFoundException(id));

        // Verificamos si la ruta tiene asignaciones (existen registros en la tabla conductor_bus_ruta):
        if (!conductorBusRutaRepository.findByRutaId(id).isEmpty()) {
            throw new RuntimeException("La ruta tiene asignaciones, no se puede eliminar.");
        }

        // Eliminamos todas las estaciones asociadas a la ruta:
        rutaEstacionRepository.deleteByRuta(ruta);

        // Eliminamos la ruta y todos sus horarios asociados:
        rutaRepository.deleteByCodigo(ruta.getCodigo());
    }
}
