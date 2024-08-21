package com.javeriana.pontimovil.ponti_movil.init;

import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.entities.Direccion;
import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.exceptions.NotFoundBusException;
import com.javeriana.pontimovil.ponti_movil.exceptions.NotFoundDireccionException;
import com.javeriana.pontimovil.ponti_movil.exceptions.NotFoundHorarioException;
import com.javeriana.pontimovil.ponti_movil.repositories.BusRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.ConductorRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.DireccionRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Profile("dev")
public class DBInitializer implements CommandLineRunner {


    // Importante: Hasta no crear todas las entidades que no dependan de otras, no podemos hacer pruebas
    // con entidades interrelacionadas (tablas intermedias)

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private ConductorRepository conductorRepository;

    // Para logs en la consola:
    private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    @Override
    public void run(String... args) throws Exception {

        // Primero vamos a crear algunos datos, para luego guardarlos en la base de datos:
        List<Bus> busesToSave = new ArrayList<>();
        List<Horario> horariosToSave = new ArrayList<>();
        List<Direccion> direccionesToSave = new ArrayList<>();
        List<Conductor> conductoresToSave = new ArrayList<>();

        // Verificamos que no existan dentro y si no existen, los agregamos a la lista para guardar:

        // Buses:
        try {
            if(busRepository.findBusByPlaca("ABC123") == null) { busesToSave.add(new Bus("ABC123", "2020")); }
        } catch (Exception e) {
            throw new NotFoundBusException("Error al buscar o crear el bus con placa ABC123: " + e.getMessage());
        }

        try {
            if(busRepository.findBusByPlaca("DEF456") == null) { busesToSave.add(new Bus("DEF456", "2021")); }
        } catch (Exception e) {
            throw new NotFoundBusException("Error al buscar o crear el bus con placa DEF456: " + e.getMessage());
        }

        try {
            if(busRepository.findBusByPlaca("GHI789") == null) { busesToSave.add(new Bus("GHI789", "2022")); }
        } catch (Exception e) {
            throw new NotFoundBusException("Error al buscar o crear el bus con placa GHI789: " + e.getMessage());
        }

        // Horarios:
        try {
            if(horarioRepository.findHorarioByDiaAndHoraInicioAndHoraFin("Lunes", LocalTime.of(6, 0), LocalTime.of(8, 0)) == null) { horariosToSave.add(new Horario("Lunes", LocalTime.of(6, 0), LocalTime.of(8, 0))); }
        } catch (Exception e) {
            throw new NotFoundHorarioException("Error al buscar o crear el horario para Lunes de 6:00 a 8:00: " + e.getMessage());
        }

        try {
            if(horarioRepository.findHorarioByDiaAndHoraInicioAndHoraFin("Martes", LocalTime.of(9, 0), LocalTime.of(11, 0)) == null) { horariosToSave.add(new Horario("Martes", LocalTime.of(9, 0), LocalTime.of(11, 0))); }
        } catch (Exception e) {
            throw new NotFoundHorarioException("Error al buscar o crear el horario para Martes de 9:00 a 11:00: " + e.getMessage());
        }

        try {
            if(horarioRepository.findHorarioByDiaAndHoraInicioAndHoraFin("Miercoles", LocalTime.of(12, 0), LocalTime.of(14, 0)) == null) { horariosToSave.add(new Horario("Miercoles", LocalTime.of(12, 0), LocalTime.of(14, 0))); }
        } catch (Exception e) {
            throw new NotFoundHorarioException("Error al buscar o crear el horario para Miércoles de 12:00 a 14:00: " + e.getMessage());
        }

        // Ahora vamos a guardar los datos en la base de datos:
        busRepository.saveAll(busesToSave);
        horarioRepository.saveAll(horariosToSave);
        logger.info("Se guardaron correctamente los datos dentro de la BD 🤯!!!");

        // Obtenemos todos los horarios del día lunes:
        List<Horario> horariosLunes = horarioRepository.findHorariosByDia("Lunes");
        logger.info("Horarios del día Lunes: {}", horariosLunes);

        // Direcciones:

        try {
            if(direccionRepository.findByCalleAndCarreraAndNumero("Calle 123", "Carrera 123", "123") == null) { direccionesToSave.add(new Direccion("Calle 123", "Carrera 123", "123", "Bogota", "Colombia")); }
        } catch (Exception e) {
            throw new NotFoundDireccionException("Error al buscar o crear la dirección con calle 123, carrera 123 y número 123: " + e.getMessage());
        }

        try {
            if(direccionRepository.findByCalleAndCarreraAndNumero("Calle 456", "Carrera 456", "456") == null) { direccionesToSave.add(new Direccion("Calle 456", "Carrera 456", "456", "Bogota", "Colombia")); }
        } catch (Exception e) {
            throw new NotFoundDireccionException("Error al buscar o crear la dirección con calle 456, carrera 456 y número 456: " + e.getMessage());
        }

        try {
            if(direccionRepository.findByCalleAndCarreraAndNumero("Calle 789", "Carrera 789", "789") == null) { direccionesToSave.add(new Direccion("Calle 789", "Carrera 789", "789", "Bogota", "Colombia")); }
        } catch (Exception e) {
            throw new NotFoundDireccionException("Error al buscar o crear la dirección con calle 789, carrera 789 y número 789: " + e.getMessage());
        }


        // Conductores:
        try {
            if(conductorRepository.findByNombreAndApellido("Juan", "Perez") == null) { conductoresToSave.add(new Conductor("Juan", "Perez", "123456", "123456", direccionRepository.findByCalleAndCarreraAndNumero("Calle 123", "Carrera 123", "123"))); }
        } catch (Exception e) {
            throw new NotFoundDireccionException("Error al buscar o crear el conductor con nombre Juan y apellido Perez: " + e.getMessage());
        }

        try {
            if(conductorRepository.findByNombreAndApellido("Pedro", "Gomez") == null) { conductoresToSave.add(new Conductor("Pedro", "Gomez", "456789", "456789", direccionRepository.findByCalleAndCarreraAndNumero("Calle 456", "Carrera 456", "456"))); }
        } catch (Exception e) {
            throw new NotFoundDireccionException("Error al buscar o crear el conductor con nombre Pedro y apellido Gomez: " + e.getMessage());
        }

        try {
            if(conductorRepository.findByNombreAndApellido("Maria", "Rodriguez") == null) { conductoresToSave.add(new Conductor("Maria", "Rodriguez", "789123", "789123", direccionRepository.findByCalleAndCarreraAndNumero("Calle 789", "Carrera 789", "789"))); }
        } catch (Exception e) {
            throw new NotFoundDireccionException("Error al buscar o crear el conductor con nombre Maria y apellido Rodriguez: " + e.getMessage());
        }

        // Guardamos los conductores en la base de datos:
        direccionRepository.saveAll(direccionesToSave);
        conductorRepository.saveAll(conductoresToSave);
        logger.info("Se guardaron correctamente los conductores en la BD 🤯!!!");



    }
}
