package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class EstacionNotFoundException extends RuntimeException {
    public EstacionNotFoundException(UUID id) {
        super("Estación con id " + id + " no encontrada");
    }
}