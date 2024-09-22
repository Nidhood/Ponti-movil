package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class DireccionNotFoundException extends RuntimeException {
    public DireccionNotFoundException(UUID id) {
        super("Dirección con id " + id + " no encontrada");
    }
}
