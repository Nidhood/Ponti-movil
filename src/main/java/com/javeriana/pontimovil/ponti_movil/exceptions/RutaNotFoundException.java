package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class RutaNotFoundException extends RuntimeException {
    public RutaNotFoundException(UUID id) {
        super("Ruta con id " + id + " no encontrada");
    }
}
