package com.javeriana.pontimovil.ponti_movil.dto.gestion_buses.bus_envio;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class bRutaEnvioDTO implements Serializable {
    UUID id;
    String codigo;
    bHoriarioEnvioDTO horario;
    Boolean dentroBus;
}