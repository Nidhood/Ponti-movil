package com.javeriana.pontimovil.ponti_movil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.entities.Role;
import com.javeriana.pontimovil.ponti_movil.dto.authentication.JwtAuthenticationResponse;
import com.javeriana.pontimovil.ponti_movil.dto.authentication.LoginDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.estacion.rDireccionDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.estacion.rEstacionDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.ruta_enviada.rEstacionEnviadaDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.ruta_enviada.rRutaEnviadaDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.ruta_recibida.rEstacionRecibidaDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.ruta_recibida.rHorarioDTO;
import com.javeriana.pontimovil.ponti_movil.entities.Direccion;
import com.javeriana.pontimovil.ponti_movil.entities.Estacion;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.UsuarioRepository;
import com.javeriana.pontimovil.ponti_movil.services.EstacionService;
import com.javeriana.pontimovil.ponti_movil.services.JwtService;
import com.javeriana.pontimovil.ponti_movil.services.RutaService;
import com.javeriana.pontimovil.ponti_movil.services.UsuarioService;

import jakarta.transaction.Transactional;

import com.javeriana.pontimovil.ponti_movil.entities.RutaEstacion;
import com.javeriana.pontimovil.ponti_movil.entities.Usuario;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaEstacionRepository;

import com.javeriana.pontimovil.ponti_movil.repositories.DireccionRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.EstacionRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.HorarioRepository;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

// To run only this test:
// ./mvnw test -Dtest=RutaControllerIntegrationTest
@ActiveProfiles("integration-testing")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class RutaControllerIntegrationTest {

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private RutaService rutaService;

    @Autowired
    private JwtService jwtService; 

    @Autowired
    private HorarioRepository horarioRepository;

     @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EstacionService estacionService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EstacionRepository estacionRepository;


    private static final String SERVER_URL = "http://localhost:8081";
    private static final String BASE_URL = SERVER_URL + "/rutas";

    @BeforeEach
    void init() {

        rutaService.eliminarTodasLasRutas();
        usuarioRepository.deleteAll();
        usuarioRepository.save(new Usuario("admin","admin","admin",passwordEncoder.encode("adminPass"),"admin@admin.com",Role.Administrador));
        usuarioRepository.save(new Usuario("pasajero","pasajero","pasajero",passwordEncoder.encode("pasajeroPass"),"pasajero@pasajero.com",Role.Pasajero));

    }

     private JwtAuthenticationResponse login(String email, String password) {
        return webTestClient.post()
                .uri(SERVER_URL + "/auth/login")
                .bodyValue(new LoginDTO(email, password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(JwtAuthenticationResponse.class)
                .returnResult()
                .getResponseBody();
    }

    @Test
    void testCrearRuta() {

        JwtAuthenticationResponse adminUsuario = login("admin@admin.com","adminPass");

        rHorarioDTO horarioPrueba = new rHorarioDTO();
        horarioPrueba.setHoraInicio(LocalTime.of(9, 0));
        horarioPrueba.setHoraFin(LocalTime.of(18,0));

        List<String> diasSemana = List.of("Lunes", "Martes", "Viernes");

        rDireccionDTO direccionPrueba = new rDireccionDTO(UUID.fromString("01bf6e52-0843-4c09-b35b-6c0086b50892"),"Calle 136","Carrera 19","No. 136-01","Usaquén","Alcalá");
        rEstacionEnviadaDTO estacionPrueba = new rEstacionEnviadaDTO(UUID.fromString("eb28f90c-8c01-4b37-957b-899b57988c06"),"Alcalá - Colegio Santo Tomás Dominicos",1,direccionPrueba);
        
        List<rEstacionEnviadaDTO> estacionesPrueba = new ArrayList<>();
        estacionesPrueba.add(estacionPrueba);

    
        rRutaEnviadaDTO nuevaRuta = new rRutaEnviadaDTO();
        UUID rutaPruebaID = UUID.randomUUID();
        nuevaRuta.setId(rutaPruebaID);
        nuevaRuta.setCodigo("P001");
        nuevaRuta.setHorario(horarioPrueba);
        nuevaRuta.setDiasSemana(diasSemana);
        nuevaRuta.setEstaciones(estacionesPrueba);


        webTestClient.post()
                .uri(BASE_URL + "/crear")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + adminUsuario.getToken())
                .bodyValue(nuevaRuta)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void testCrearRutaSinLogin() {
        rHorarioDTO horarioPrueba = new rHorarioDTO();
        horarioPrueba.setHoraInicio(LocalTime.of(9, 0));
        horarioPrueba.setHoraFin(LocalTime.of(18,0));

        List<String> diasSemana = List.of("Lunes", "Martes", "Viernes");

        rDireccionDTO direccionPrueba = new rDireccionDTO(UUID.fromString("01bf6e52-0843-4c09-b35b-6c0086b50892"),"Calle 136","Carrera 19","No. 136-01","Usaquén","Alcalá");
        rEstacionEnviadaDTO estacionPrueba = new rEstacionEnviadaDTO(UUID.fromString("eb28f90c-8c01-4b37-957b-899b57988c06"),"Alcalá - Colegio Santo Tomás Dominicos",1,direccionPrueba);
        
        List<rEstacionEnviadaDTO> estacionesPrueba = new ArrayList<>();
        estacionesPrueba.add(estacionPrueba);

    
        rRutaEnviadaDTO nuevaRuta = new rRutaEnviadaDTO();
        UUID rutaPruebaID = UUID.randomUUID();
        nuevaRuta.setId(rutaPruebaID);
        nuevaRuta.setCodigo("P001");
        nuevaRuta.setHorario(horarioPrueba);
        nuevaRuta.setDiasSemana(diasSemana);
        nuevaRuta.setEstaciones(estacionesPrueba);


        webTestClient.post()
                .uri(BASE_URL + "/crear")
                .bodyValue(nuevaRuta)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void testCrearRutaSinAutorizacion() {

        JwtAuthenticationResponse pasajeroUsuario = login("pasajero@pasajero.com","pasajeroPass");

        rHorarioDTO horarioPrueba = new rHorarioDTO();
        horarioPrueba.setHoraInicio(LocalTime.of(9, 0));
        horarioPrueba.setHoraFin(LocalTime.of(18,0));

        List<String> diasSemana = List.of("Lunes", "Martes", "Viernes");

        rDireccionDTO direccionPrueba = new rDireccionDTO(UUID.fromString("01bf6e52-0843-4c09-b35b-6c0086b50892"),"Calle 136","Carrera 19","No. 136-01","Usaquén","Alcalá");
        rEstacionEnviadaDTO estacionPrueba = new rEstacionEnviadaDTO(UUID.fromString("eb28f90c-8c01-4b37-957b-899b57988c06"),"Alcalá - Colegio Santo Tomás Dominicos",1,direccionPrueba);
        
        List<rEstacionEnviadaDTO> estacionesPrueba = new ArrayList<>();
        estacionesPrueba.add(estacionPrueba);

    
        rRutaEnviadaDTO nuevaRuta = new rRutaEnviadaDTO();
        UUID rutaPruebaID = UUID.randomUUID();
        nuevaRuta.setId(rutaPruebaID);
        nuevaRuta.setCodigo("P001");
        nuevaRuta.setHorario(horarioPrueba);
        nuevaRuta.setDiasSemana(diasSemana);
        nuevaRuta.setEstaciones(estacionesPrueba);


        webTestClient.post()
                .uri(BASE_URL + "/crear")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + pasajeroUsuario.getToken())
                .bodyValue(nuevaRuta)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void testConsultarRuta(){

        JwtAuthenticationResponse pasajeroUsuario = login("pasajero@pasajero.com","pasajeroPass");
        testCrearRuta();

        webTestClient.get()
                .uri(BASE_URL)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + pasajeroUsuario.getToken())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Ruta.class)
                .value(rutas -> {
                    assertEquals(rutas.get(0).getCodigo(), "P001");
                });
        

    }
}
