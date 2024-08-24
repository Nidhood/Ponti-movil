package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class ConductorNotFoundException extends RuntimeException {
    public ConductorNotFoundException(UUID id) {
        super("Conductor con id " + id + " no encontrado");
    }
}
