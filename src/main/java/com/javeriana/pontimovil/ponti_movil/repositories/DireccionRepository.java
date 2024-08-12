package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.Direccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DireccionRepository extends JpaRepository<Direccion, UUID> {
}