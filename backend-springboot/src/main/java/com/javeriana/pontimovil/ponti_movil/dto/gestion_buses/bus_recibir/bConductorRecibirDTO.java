package com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_recibir;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class bConductorRecibirDTO implements Serializable {
    UUID id;
    String nombre;
    String apellido;
}