package com.javeriana.pontimovil.ponti_movil;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import com.javeriana.pontimovil.ponti_movil.dto.gestion_rutas.ruta_enviada.rRutaEnviadaDTO;
import com.javeriana.pontimovil.ponti_movil.entities.Direccion;


import com.javeriana.pontimovil.ponti_movil.repositories.RutaRepository;
import com.javeriana.pontimovil.ponti_movil.services.RutaService;
import com.javeriana.pontimovil.ponti_movil.entities.RutaEstacion;
import com.javeriana.pontimovil.ponti_movil.repositories.RutaEstacionRepository;

import com.javeriana.pontimovil.ponti_movil.repositories.DireccionRepository;
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
    private HorarioRepository horarioRepository;

    private static final String SERVER_URL = "http://localhost:8081";
    private static final String BASE_URL = SERVER_URL+ "/rutas";

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void init(){

    }

    @Test
    void testCrearRuta(){
        rRutaEnviadaDTO nuevaRuta = new rRutaEnviadaDTO();
        UUID rutaPruebaID = UUID.randomUUID();
        nuevaRuta.setId(rutaPruebaID);
        nuevaRuta.setCodigo("P001");
        
        ResponseEntity<Void> response = restTemplate.postForEntity(BASE_URL + "/crear", nuevaRuta, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        Ruta ruta = rutaService.obtenerRutaPorId(rutaPruebaID);
        assertEquals("P001", ruta.getCodigo());

    }





    
}
