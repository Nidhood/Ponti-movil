package com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_envio;

import lombok.*;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class bHoriarioEnvioDTO implements Serializable {
    UUID id;
    LocalTime horaInicio;
    LocalTime horaFin;
}