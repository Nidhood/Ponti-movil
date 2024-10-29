package com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_envio;

import lombok.*;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class bBusEnvioDTO implements Serializable {
    UUID id;
    String placa;
    String modelo;
    List<bRutaEnvioDTO> rutas;
}