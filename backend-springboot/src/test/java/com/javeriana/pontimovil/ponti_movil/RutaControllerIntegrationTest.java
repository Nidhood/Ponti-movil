package com.javeriana.pontimovil.ponti_movil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.repositories.HorarioRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class RutaControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private HorarioRepository horarioRepository;

    @BeforeEach
    void setup() {
        // Crear un horario, guardarlo y asignarlo a la ruta
        Horario horario = new Horario();
        horario.setHoraInicio(LocalTime.of(6, 0));
        horario.setHoraFin(LocalTime.of(18, 0));
        horarioRepository.save(horario); // Guardar primero el horario en la base de datos

        Ruta ruta = new Ruta();
        ruta.setCodigo("ruta-prueba-1");
        ruta.setHorario(horario);

        rutaRepository.save(ruta);
    }

    @Test
    void obtenerRutasTest() {
        webTestClient
                .get()
                .uri("/api/rutas")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Ruta.class)
                .value(rutas -> {
                    assertEquals(1, rutas.size());
                    assertEquals("ruta-prueba-1", rutas.get(0).getCodigo());
                });
    }

    @Test
    void actualizarRutaTest() {
        // Obtenemos la ruta guardada en la base de datos
        Ruta rutaExistente = rutaRepository.findAll().get(0);
        UUID idRuta = rutaExistente.getId();

        // Crear y guardar un horario actualizado
        Horario horarioActualizado = new Horario();
        horarioActualizado.setHoraInicio(LocalTime.of(8, 0));
        horarioActualizado.setHoraFin(LocalTime.of(20, 0));
        horarioRepository.save(horarioActualizado); // Guardar primero el horario

        // Crear la ruta actualizada
        Ruta rutaActualizada = new Ruta();
        rutaActualizada.setCodigo("ruta-actualizada");
        rutaActualizada.setHorario(horarioActualizado);

        // Realizar la petición PUT para actualizar la ruta
        webTestClient
                .put()
                .uri("/api/rutas/" + idRuta)
                .bodyValue(rutaActualizada)
                .exchange()
                .expectStatus().isOk();

        // Verificar que la ruta se actualizó en la base de datos
        Ruta rutaActualizadaDB = rutaRepository.findById(idRuta).orElseThrow();
        assertEquals("ruta-actualizada", rutaActualizadaDB.getCodigo());
        assertEquals(LocalTime.of(8, 0), rutaActualizadaDB.getHorario().getHoraInicio());
        assertEquals(LocalTime.of(20, 0), rutaActualizadaDB.getHorario().getHoraFin());
    }

    @Test
    void eliminarRutaTest() {
        // Obtenemos la ruta guardada en la base de datos
        Ruta rutaExistente = rutaRepository.findAll().get(0);
        UUID idRuta = rutaExistente.getId();

        // Realizar la petición DELETE para eliminar la ruta
        webTestClient
                .delete()
                .uri("/api/rutas/" + idRuta)
                .exchange()
                .expectStatus().isOk();

        // Verificar que la ruta fue eliminada de la base de datos
        List<Ruta> rutas = rutaRepository.findAll();
        assertEquals(0, rutas.size());
    }
}
