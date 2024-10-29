package com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_recibir;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class bRutaRecibirDTO implements Serializable {
    UUID id;
    String codigo;
}