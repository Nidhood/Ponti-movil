package com.javeriana.pontimovil.ponti_movil.dto.pasajero;

import lombok.*;
import java.io.Serializable;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class HorarioDTO implements Serializable {
    String dia;
    LocalTime horaInicio;
    LocalTime horaFin;
}