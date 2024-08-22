package com.javeriana.pontimovil.ponti_movil.init;

import com.javeriana.pontimovil.ponti_movil.entities.*;
import com.javeriana.pontimovil.ponti_movil.exceptions.NotFoundBusException;
import com.javeriana.pontimovil.ponti_movil.exceptions.NotFoundDireccionException;
import com.javeriana.pontimovil.ponti_movil.exceptions.NotFoundHorarioException;
import com.javeriana.pontimovil.ponti_movil.repositories.*;
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


    // Para logs en la consola:
    private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);

    @Override
    public void run(String... args) throws Exception {

        // Primero vamos a crear algunos datos, para luego guardarlos en la base de datos:
        List<Bus> busesToSave = new ArrayList<>();
        List<Horario> horariosToSave = new ArrayList<>();

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
    }
}
