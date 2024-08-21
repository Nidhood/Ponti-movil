package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.TiposUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TiposUsuarioRepository extends JpaRepository<TiposUsuario, UUID> {
}