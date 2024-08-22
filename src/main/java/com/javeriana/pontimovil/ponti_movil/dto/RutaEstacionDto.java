package com.javeriana.pontimovil.ponti_movil.dto;

import com.javeriana.pontimovil.ponti_movil.entities.Estacion;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.javeriana.pontimovil.ponti_movil.entities.RutaEstacion}
 */

@Getter
@Setter
@Value
public class RutaEstacionDto implements Serializable {
    Long id;
    RutaDto ruta;
    Estacion estacion;
    Long orden;
}