package com.javeriana.pontimovil.ponti_movil.dto.pasajero;

import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import lombok.*;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
@NoArgsConstructor
public class RutaDTO implements Serializable {
    String codigo;
    HorarioDTO horario;
    List<EstacionDTO> estaciones;
    List<BusDTO> buses;
    List<ConductorDTO> conductores;
}