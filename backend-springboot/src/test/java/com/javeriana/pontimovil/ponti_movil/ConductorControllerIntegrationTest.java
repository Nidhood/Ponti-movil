package com.javeriana.pontimovil.ponti_movil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

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

import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado.cBusEnviandoDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado.cConductorEnviandoDTO;
import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_recibiendo.cConductorRecibiendoDTO;
import com.javeriana.pontimovil.ponti_movil.entities.Conductor;
import com.javeriana.pontimovil.ponti_movil.entities.Direccion;
import com.javeriana.pontimovil.ponti_movil.services.ConductorService;
import com.javeriana.pontimovil.ponti_movil.repositories.ConductorRepository;
import com.javeriana.pontimovil.ponti_movil.repositories.DireccionRepository;


//Para correr todas al mismo tiempo se debe usar mvn test -Dtest=com.javeriana.pontimovil.ponti_movil.ConductorControllerIntegrationTest
@ActiveProfiles("integration-testing")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ConductorControllerIntegrationTest {

    @Autowired
    private ConductorRepository conductorRepository;

    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String SERVER_URL = "http://localhost:8081";

    private Conductor conductorPrueba;

    @BeforeEach
    void init(){
        Direccion direccionPrueba = new Direccion("Calle 1","Carrera 2","23-45","Suba","Colina campestre");
        direccionRepository.save(direccionPrueba);
        conductorPrueba = conductorRepository.save(new Conductor("Juan","Perez","1234567890","3123456789",direccionPrueba));
    }

    @Test
    void traerConductorPorId() {

        
         // Define the URL with the ID of the saved conductor
         String url = "/conductores/" + conductorPrueba.getId();

         // Send a GET request to fetch the conductor by ID
         ResponseEntity<Conductor> response = restTemplate.getForEntity(url, Conductor.class);
 
         // Verify the response
         assertEquals(HttpStatus.OK, response.getStatusCode());
         Conductor conductor = response.getBody();
         assertNotNull(conductor);
         assertEquals(conductorPrueba.getNombre(), conductor.getNombre());
         assertEquals(conductorPrueba.getApellido(), conductor.getApellido());
         assertEquals(conductorPrueba.getTelefono(), conductor.getTelefono());
    }


    
}

