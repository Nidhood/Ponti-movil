package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.AsignacionesBusRuta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AsignacionesBusesRutaRepository extends JpaRepository<AsignacionesBusRuta, UUID> {
}