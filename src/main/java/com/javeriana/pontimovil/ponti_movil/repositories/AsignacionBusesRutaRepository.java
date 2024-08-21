package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.AsignacionBusRuta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface AsignacionBusesRutaRepository extends JpaRepository<AsignacionBusRuta, UUID> {
}