package com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_recibiendo;

import lombok.*;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class cHorarioEnviandoDTO implements Serializable {
    UUID id;
    LocalTime horaInicio;
    LocalTime horaFin;
}