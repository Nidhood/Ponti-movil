package com.javeriana.pontimovil.ponti_movil.exceptions;

import java.util.UUID;

public class UsuarioNotFoundException extends RuntimeException {
    public UsuarioNotFoundException(UUID id) {
        super("Usuario con id " + id + " no encontrado");
    }
}
