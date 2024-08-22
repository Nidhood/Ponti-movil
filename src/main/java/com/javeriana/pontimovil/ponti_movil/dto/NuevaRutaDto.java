package com.javeriana.pontimovil.ponti_movil.dto;

import com.javeriana.pontimovil.ponti_movil.entities.Horario;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link RutaEstacionDto}
 */

@Getter
@Setter
@Value
public class NuevaRutaDto implements Serializable {
    Long id;
    List<RutaEstacionDto> rutasEstacion;
    Horario horario;
}