package com.javeriana.pontimovil.ponti_movil.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.Value;

import java.io.Serializable;
import java.util.UUID;

/**
 * DTO for {@link com.javeriana.pontimovil.ponti_movil.entities.Ruta}
 */

@Getter
@Setter
@Value
public class RutaDto implements Serializable {
    UUID id;
    String codigo;
}