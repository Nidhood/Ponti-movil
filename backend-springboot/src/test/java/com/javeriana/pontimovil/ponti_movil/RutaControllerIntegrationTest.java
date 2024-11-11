package com.javeriana.pontimovil.ponti_movil;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpStatus.OK;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.ruta_recibida.rHorarioDTO;
import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.HorarioRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaEstacionRepository;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.ruta_enviada.rRutaEnviadaDTO;

// To run only this test:
// ./mvnw test -Dtest=RutaControllerIntegrationTest
@ActiveProfiles("integration-testing")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class RutaControllerIntegrationTest {

    private final String SERVER_URL;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private HorarioRepository horarioRepository;

    @Autowired
    private RutaEstacionRepository rutaEstacionRepository;

    @Autowired
    private RutaRepository rutaRepository;

    public RutaControllerIntegrationTest(@Value("${server.port:8081}") int serverPort) {
        this.SERVER_URL = "http://localhost:" + serverPort;
    }

    @BeforeEach
    public void init() {
        
    }

    @Test
    public void testCrearRuta() {
        rHorarioDTO horario = new rHorarioDTO(UUID.randomUUID(),LocalTime.of(8, 0), LocalTime.of(18, 0));
        // horarioRepository.save(horario); // Guarda el horario para asociarlo con la ruta
    
        rRutaEnviadaDTO nuevaRuta = new rRutaEnviadaDTO(
                UUID.randomUUID(),
                "B10",
                horario,
                List.of("Lunes", "Martes", "Miércoles"),
                List.of()
        );
    
        webTestClient.post().uri("/rutas/crear")
                .bodyValue(nuevaRuta)
                .exchange()
                .expectStatus().isOk();
    
        List<Ruta> rutas = rutaRepository.findAll();
        assertEquals(1, rutas.size());
        assertEquals("B10", rutas.get(0).getCodigo());
        assertEquals(horario.getHoraInicio(), rutas.get(0).getHorario().getHoraInicio());
        assertEquals(horario.getHoraFin(), rutas.get(0).getHorario().getHoraFin());
    }

    @Test
    public void testActualizarRuta() {
        // Crear y guardar una ruta y horario inicial
        Horario horarioInicial = new Horario(LocalTime.of(9, 0), LocalTime.of(17, 0));
        horarioRepository.save(horarioInicial);

        Ruta ruta = new Ruta();
        UUID idRuta = UUID.randomUUID();
        ruta.setId(idRuta);
        ruta.setCodigo("R001");
        ruta.setHorario(horarioInicial);
        rutaRepository.save(ruta);

        // Definir los nuevos datos de horario para actualizar
        rHorarioDTO nuevoHorario = new rHorarioDTO(UUID.randomUUID(), LocalTime.of(9, 0), LocalTime.of(17, 0));
    
        rRutaEnviadaDTO rutaActualizada = new rRutaEnviadaDTO(
            idRuta,
            "R002",
            nuevoHorario,
            List.of("Lunes", "Viernes"),
            List.of()
        );

        webTestClient.post().uri("/rutas/" + ruta.getId() + "/actualizar")
            .bodyValue(rutaActualizada)
            .exchange()
            .expectStatus().isOk();

        Ruta rutaActualizadaBD = rutaRepository.findById(ruta.getId()).orElse(null);
        assertEquals("R002", rutaActualizadaBD.getCodigo());
        assertEquals(nuevoHorario.getHoraInicio(), rutaActualizadaBD.getHorario().getHoraInicio());
        assertEquals(nuevoHorario.getHoraFin(), rutaActualizadaBD.getHorario().getHoraFin());
    }

    @Test
    public void testEliminarRuta() {
        Horario horario = new Horario(LocalTime.of(8, 0), LocalTime.of(17, 0));
        horarioRepository.save(horario);

        rRutaEnviadaDTO nuevaRuta = new rRutaEnviadaDTO(
            UUID.randomUUID(),
            "R001",
            new rHorarioDTO(horario.getId(), horario.getHoraInicio(), horario.getHoraFin()),
            List.of("Lunes", "Martes"),
            List.of()
        );

        webTestClient.post().uri("/rutas/crear")
            .bodyValue(nuevaRuta)
            .exchange()
            .expectStatus().isOk();

        List<Ruta> rutas = rutaRepository.findAll();
        assertEquals(1, rutas.size());

        webTestClient.delete().uri("/rutas/" + rutas.get(0).getId()+ "/eliminar")
            .exchange()
            .expectStatus().isOk();

        // Verificar que la ruta fue eliminada
        boolean rutaEliminada = rutaRepository.findById(rutas.get(0).getId()).isEmpty();
        assertEquals(true, rutaEliminada);
    }
 
}