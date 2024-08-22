package com.javeriana.pontimovil.ponti_movil.services;


import com.javeriana.pontimovil.ponti_movil.dto.NuevaRutaDto;
import com.javeriana.pontimovil.ponti_movil.dto.RutaEstacionDto;
import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.entities.RutaEstacion;
import com.javeriana.pontimovil.ponti_movil.repositories.HorarioRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaEstacionRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RutaService {

    // Repositorio:
    RutaRepository rutaRepository;
    HorarioRepository horarioRepository;
    RutaEstacionRepository rutaEstacionRepository;

    // Constructor:
    @Autowired
    public RutaService(RutaRepository rutaRepository, HorarioRepository horarioRepository, RutaEstacionRepository rutaEstacionRepository) {
        this.rutaRepository = rutaRepository;
        this.horarioRepository = horarioRepository;
        this.rutaEstacionRepository = rutaEstacionRepository;
    }

    // Métodos:
    public List<Ruta> obtenerRutas() {
        return rutaRepository.findAll();
    }

    public Ruta obtenerRutaPorId(UUID id) {
        return rutaRepository.findById(id).orElseThrow(()-> new RuntimeException("Ruta no encontrada"));
    }

    public void crearRuta(NuevaRutaDto nuevaRutaDto) {
        Horario horario = new Horario();
        List<Ruta> rutas = new ArrayList<>();
        List<RutaEstacion> rutasEstacion = new ArrayList<>();

        // Creamos un nuevo horario si aun no existe:
        if(nuevaRutaDto.getHorario() != null){
            horario.setDia(nuevaRutaDto.getHorario().getDia());
            horario.setHoraInicio(nuevaRutaDto.getHorario().getHoraInicio());
            horario.setHoraFin(nuevaRutaDto.getHorario().getHoraFin());
            horarioRepository.save(horario);
        }

        // Creamos las rutas y su relación las estaciones:
        for (RutaEstacionDto rutaEstacionDto: nuevaRutaDto.getRutasEstacion()) {

            // Creamos nueva ruta:
            Ruta ruta = new Ruta();
            ruta.setCodigo(rutaEstacionDto.getRuta().getCodigo());
            ruta.setHorario(horario);
            rutas.add(ruta);

            // Creamos nueva ruta estación:
            RutaEstacion rutaEstacion = new RutaEstacion();
            rutaEstacion.setRuta(ruta);
            rutaEstacion.setEstacion(rutaEstacionDto.getEstacion());
            rutaEstacion.setOrden(rutaEstacionDto.getOrden());
            rutasEstacion.add(rutaEstacion);
        }

        // Guardamos las rutas y las rutas estación:
        rutaRepository.saveAll(rutas);
        rutaEstacionRepository.saveAll(rutasEstacion);
    }

    public void actualizarRuta(String codigo, NuevaRutaDto nuevaRutaDto) {
        Horario horario = horarioRepository.findById(nuevaRutaDto.getHorario().getId()).orElseThrow(() -> new RuntimeException("Horario no encontrado"));
        List<RutaEstacion> rutasEstacion = rutaEstacionRepository.findAllByRutaCodigo(codigo);

        // Actualizamos el horario de la ruta:
        horario.setDia(nuevaRutaDto.getHorario().getDia());
        horario.setHoraInicio(nuevaRutaDto.getHorario().getHoraInicio());
        horario.setHoraFin(nuevaRutaDto.getHorario().getHoraFin());
        horarioRepository.save(horario);

        // Actualizamos todas las estaciones de la ruta:

        // Primero eliminamos todas las estaciones de la ruta:
        rutaEstacionRepository.deleteAll(rutasEstacion);

        // Luego actualizamos las estaciones de la ruta:
        for (RutaEstacionDto rutaEstacionDto: nuevaRutaDto.getRutasEstacion()) {
            RutaEstacion rutaEstacion = new RutaEstacion();
            rutaEstacion.setRuta(rutaRepository.findByCodigo(codigo));
            rutaEstacion.setEstacion(rutaEstacionDto.getEstacion());
            rutaEstacion.setOrden(rutaEstacionDto.getOrden());
            rutasEstacion.add(rutaEstacion);
        }

        // Guardamos las rutas estación:
        rutaEstacionRepository.saveAll(rutasEstacion);
    }

    public void eliminarRuta(String codigo) {
        Ruta ruta = rutaRepository.findByCodigo(codigo);
        List<RutaEstacion> rutasEstacion = rutaEstacionRepository.findAllByRutaCodigo(codigo);

        // Eliminamos todas las estaciones de la ruta:
        rutaEstacionRepository.deleteAll(rutasEstacion);

        // Eliminamos la ruta:
        rutaRepository.delete(ruta);
    }
}
