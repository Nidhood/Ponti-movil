package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.ConductorBusRuta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ConductorBusRutaRepository extends JpaRepository<ConductorBusRuta, UUID> {
    ConductorBusRuta findByConductorIdAndBusId(UUID idConductor, UUID idBus);
    ConductorBusRuta findByBusIdAndRutaId(UUID idBus, UUID idRuta);
    List<ConductorBusRuta> findByRutaId(UUID id);
}