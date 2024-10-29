package com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_recibiendo;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class cRutaRecibiendoDTO implements Serializable {
    UUID id;
    String codigo;
}