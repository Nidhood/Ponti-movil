package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.AsignacionConductorBus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AsignacionConductorBusRepository extends JpaRepository<AsignacionConductorBus, UUID> {
    AsignacionConductorBus findAsignacionConductorBusByIdAndDiaSemana(UUID id, String dia);
}