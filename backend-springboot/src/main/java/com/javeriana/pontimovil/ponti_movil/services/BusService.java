package com.javeriana.pontimovil.ponti_movil.services;

import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_envio.bHoriarioEnvioDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_envio.bRutaEnvioDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_recibir.bBusRecibirDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_recibir.bConductorRecibirDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_recibir.bRutaRecibirDTO;
import com.javeriana.pontimovil.ponti_movil.entities.Asignacion;
import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.exceptions.BusNotFoundException;
import com.javeriana.pontimovil.ponti_movil.exceptions.RutaNotFoundException;
import com.javeriana.pontimovil.ponti_movil.repositories.AsignacionRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.BusRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.ConductorRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BusService {

    private final ConductorRepository conductorRepository;
    // Repositorio:
    BusRepository busRepository;
    AsignacionRepository asignacionRepository;
    RutaRepository rutaRepository;

    // Constructor:
    @Autowired
    public BusService(BusRepository busRepository, AsignacionRepository asignacionRepository, RutaRepository rutaRepository, ConductorRepository conductorRepository) {
        this.busRepository = busRepository;
        this.asignacionRepository = asignacionRepository;
        this.rutaRepository = rutaRepository;
        this.conductorRepository = conductorRepository;
    }

    // Métodos:
    public List<bBusRecibirDTO> obtenerBuses() {
        List<bBusRecibirDTO> buses = new ArrayList<>();
        
        // Obtenemos los buses:
        List<Bus> busList = busRepository.findAll();
        
        // Para cada bus creamos un nuevo DTO:
        for (Bus bus : busList) {

            // Obtenemos la lista de dias de la semana de las asignaciones del bus (sin repetir):
            List<String> diasSemana = mapToDiasSemana(asignacionRepository.findByBusId(bus.getId()).stream().distinct().map(Asignacion::getDiaSemana).toList());

            // Obtenemos la lista de rutas del bus:
            List<bRutaRecibirDTO> rutas = mapToRutaRecibirDTO(asignacionRepository.findByBusId(bus.getId()).stream().distinct().map(Asignacion::getRuta).toList());
            
            // Obtenemos la lista de conductores del bus:
            List<bConductorRecibirDTO> conductores = mapToCoductorRecibirDTO(asignacionRepository.findByBusId(bus.getId()).stream().distinct().map(Asignacion::getConductor).toList());
            
            // Creamos el DTO:
            bBusRecibirDTO busDTO = new bBusRecibirDTO(
                    bus.getId(),
                    bus.getPlaca(),
                    bus.getModelo(),
                    diasSemana,
                    rutas,
                    conductores
            );
            
            // Agregamos el DTO a la lista de buses:
            buses.add(busDTO);
        }
        
        // Retornamos la lista de buses:
        return buses;
    }

    public List<String> mapToDiasSemana(List<String> diasSemana) {
        // Mapeamos los días de la semana en el nuevo formato DTO, omitiendo los nulos:
        return ordenarDiasSemana(diasSemana.stream()
                .distinct() // Elimina los elementos duplicados
                .filter(Objects::nonNull) // Filtra los elementos nulos
                .toList()
        );
    }

    private List<String> ordenarDiasSemana(List<String> diasSemana) {
        final List<String> ordenDias = Arrays.asList("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo");

        return diasSemana.stream()
                .sorted(Comparator.comparingInt(ordenDias::indexOf))
                .collect(Collectors.toList());
    }

    public List<bRutaRecibirDTO> mapToRutaRecibirDTO(List<Ruta> rutas) {
        // Mapeamos las rutas en el nuevo formato DTO, omitiendo los nulos:
        return rutas.stream()
                .distinct() // Elimina los elementos duplicados
                .filter(Objects::nonNull) // Filtra los elementos nulos
                .map(ruta -> new bRutaRecibirDTO(
                        ruta.getId(),
                        ruta.getCodigo()
                ))
                .toList();
    }

    public List<bConductorRecibirDTO> mapToCoductorRecibirDTO(List<Conductor> conductores) {
        // Mapeamos los conductores en el nuevo formato DTO, omitiendo los nulos:
        return conductores.stream()
                .distinct() // Elimina los elementos duplicados
                .filter(Objects::nonNull) // Filtra los elementos nulos
                .map(conductor -> new bConductorRecibirDTO(
                        conductor.getId(),
                        conductor.getNombre(),
                        conductor.getApellido()
                ))
                .toList();
    }

    public List<bRutaEnvioDTO> obtenerRutas() {
        List<bRutaEnvioDTO> rutas = new ArrayList<>();

        // Obtenemos todas las rutas:
        List<Ruta> rutaList = rutaRepository.findAll();

        // Para cada ruta, creamos un nuevo DTO:
        for (Ruta ruta : rutaList) {

            // Creamos el DTO:
            bRutaEnvioDTO rutaNueva = new bRutaEnvioDTO(
                    ruta.getId(),
                    ruta.getCodigo(),
                    new bHoriarioEnvioDTO(
                            ruta.getId(),
                            ruta.getHorario().getHoraInicio(),
                            ruta.getHorario().getHoraFin()
                    ),
                    false
            );

            // Agregamos el DTO a la lista de rutas:
            rutas.add(rutaNueva);
        }

        // Retornamos la lista de rutas:
        return rutas;
    }

    public List<bRutaEnvioDTO> obtenerRutasPorBus(UUID id) {
        List<bRutaEnvioDTO> rutas = new ArrayList<>();

        // Obtenemos todas las rutas:
        List<Ruta> rutaList = rutaRepository.findAll();

        // Obtenemos el bus:
        Bus bus = busRepository.findById(id).orElseThrow(()-> new BusNotFoundException(id));

        // Para cada ruta, creamos un nuevo DTO:
        for (Ruta ruta : rutaList) {

            // Creamos el DTO:
            bRutaEnvioDTO rutaNueva = new bRutaEnvioDTO(
                    ruta.getId(),
                    ruta.getCodigo(),
                    new bHoriarioEnvioDTO(
                            ruta.getId(),
                            ruta.getHorario().getHoraInicio(),
                            ruta.getHorario().getHoraFin()
                    ),
                    asignacionRepository.existsByBusAndRuta(bus, ruta)
            );

            // Agregamos el DTO a la lista de rutas:
            rutas.add(rutaNueva);
        }

        // Retornamos la lista de rutas:
        return rutas;
    }


    public Bus obtenerBusPorId(UUID id) {
        return busRepository.findById(id).orElseThrow(()-> new BusNotFoundException(id));
    }

    @Transactional
    public void crearBus(bBusRecibirDTO bus) {
        Bus busNuevo = new Bus();

        // Obtenemos las rutas:
        List<Ruta> rutas = rutaRepository.findAllById(bus.getRutas().stream().map(bRutaRecibirDTO::getId).toList());

        // Creamos el bus:
        busNuevo.setPlaca(bus.getPlaca());
        busNuevo.setModelo(bus.getModelo());
        busRepository.save(busNuevo);

        // Con base a los dias de la semana, creamos las asignaciones para el bus con cada ruta:
        manejarAsignaciones(busNuevo, bus.getRutas(), bus.getDiasSemana());
    }

    @Transactional
    public void actualizarBus(UUID id, bBusRecibirDTO bus) {
        Bus busActual = busRepository.findById(id).orElseThrow(() -> new BusNotFoundException(id));

        // Actualizamos el bus:
        busActual.setPlaca(bus.getPlaca());
        busActual.setModelo(bus.getModelo());
        busRepository.save(busActual);

        // Actualizamos las asignaciones de buses y rutas:
        manejarAsignaciones(busActual, bus.getRutas(), bus.getDiasSemana());
    }

    @Transactional
    public void manejarAsignaciones(Bus busExistente, List<bRutaRecibirDTO> rutas, List<String> diasSemana) {
        List<String> todosDias = Arrays.asList("Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado", "Domingo");

        // Manejamos las asignaciones para las rutas en los días especificados
        for (String dia : diasSemana) {
            for (bRutaRecibirDTO ruta : rutas) {
                Ruta rutaExistente = rutaRepository.findById(ruta.getId()).orElseThrow(() -> new RutaNotFoundException(ruta.getId()));
                List<Asignacion> asignacionesExistentes = asignacionRepository.findByBusIdAndRutaIdAndDiaSemana(busExistente.getId(), rutaExistente.getId(), dia);

                if (asignacionesExistentes.isEmpty()) {
                    // Si no existe la asignación, la creamos
                    Asignacion nuevaAsignacion = new Asignacion();
                    nuevaAsignacion.setBus(busExistente);
                    nuevaAsignacion.setRuta(rutaExistente);
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
            List<Asignacion> asignacionesAEliminar = asignacionRepository.findByBusIdAndDiaSemana(busExistente.getId(), dia);
            for (Asignacion asignacion : asignacionesAEliminar) {
                if (asignacion.getConductor() != null) {
                    // Si hay un conductor asignado, solo desvinculamos el bus y la ruta
                    asignacion.setBus(null);
                    asignacion.setRuta(null);
                    asignacionRepository.save(asignacion);
                } else {
                    // Si no hay conductor asignado, eliminamos la asignación
                    asignacionRepository.delete(asignacion);
                }
            }
        }
    }

    @Transactional
    public void eliminarBus(UUID id) {
        Bus bus = busRepository.findById(id).orElseThrow(()-> new BusNotFoundException(id));
        List<Asignacion> asignacions = asignacionRepository.findByBus(bus);

        // Primero eliminamos todas las asignaciones del bus:
        for (Asignacion asignacion : asignacions) {

            // Si no tiene asignaciones asociadas (solamente existe el bus) eliminamos la asignación:
            if (asignacion.getConductor() == null || asignacion.getRuta() == null) {
                asignacionRepository.delete(asignacion);
            }

            // Si tiene asignaciones asociadas (solo eliminamos el bus dentro de cada asignación):
            else {
                asignacion.setBus(null);
                asignacionRepository.save(asignacion);
            }
        }

        // Eliminamos el bus:
        busRepository.delete(bus);
    }
}
