package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class BusNotFoundException extends RuntimeException {
    public BusNotFoundException(UUID id) {
        super("Bus con id " + id + " no encontrado");
    }
}