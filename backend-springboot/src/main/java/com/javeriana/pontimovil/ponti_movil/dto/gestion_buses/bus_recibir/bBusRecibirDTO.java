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
public class bBusRecibirDTO implements Serializable {
    UUID id;
    String placa;
    String modelo;
    List<String> diasSemana;
    List<bRutaRecibirDTO> rutas;
    List<bConductorRecibirDTO> conductores;
}