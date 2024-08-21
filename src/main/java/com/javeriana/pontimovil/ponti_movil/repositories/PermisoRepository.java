package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.Permiso;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PermisoRepository extends JpaRepository<Permiso, UUID> {
}