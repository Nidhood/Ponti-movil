package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class AsignacionNotFoundException extends RuntimeException {
    public AsignacionNotFoundException(UUID id) {
        super("Asignación con id " + id + " no encontrada");
    }
}
