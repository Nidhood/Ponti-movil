package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.AsignacionConductorBus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AsignacionConductorBusRepository extends JpaRepository<AsignacionConductorBus, UUID> {
}