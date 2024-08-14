package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RutaRepository extends JpaRepository<Ruta, UUID> {
}