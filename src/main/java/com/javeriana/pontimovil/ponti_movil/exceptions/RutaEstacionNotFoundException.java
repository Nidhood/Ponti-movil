package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class RutaEstacionNotFoundException extends RuntimeException {
    public RutaEstacionNotFoundException(UUID id) {
        super("RutaEstacion con id " + id + " no encontrada");
    }
}
