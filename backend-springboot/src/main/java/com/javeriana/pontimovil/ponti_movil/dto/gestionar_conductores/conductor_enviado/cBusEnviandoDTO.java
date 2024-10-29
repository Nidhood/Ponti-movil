package com.javeriana.pontimovil.ponti_movil.dto.gestionar_conductores.conductor_enviado;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class cBusEnviandoDTO implements Serializable {
    UUID id;
    String placa;
    String modelo;
    Boolean dentroConductor;
}