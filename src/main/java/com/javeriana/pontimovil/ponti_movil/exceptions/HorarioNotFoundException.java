package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class HorarioNotFoundException extends RuntimeException {
    public HorarioNotFoundException(UUID id) {
        super("Horario con id " + id + " no encontrado");
    }
}