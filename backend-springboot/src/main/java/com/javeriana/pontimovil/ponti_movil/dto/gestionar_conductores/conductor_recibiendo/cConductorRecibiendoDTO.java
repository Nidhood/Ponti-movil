package com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_recibiendo;

import com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado.cBusEnviandoDTO;
import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class cConductorRecibiendoDTO implements Serializable {
    UUID id;
    String nombre;
    String apellido;
    String cedula;
    String telefono;
    cDireccionRecibiendoDTO direccion;
    List<String> diasSemana;
    List<cBusEnviandoDTO> buses;
}