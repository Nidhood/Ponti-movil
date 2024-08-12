package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.Bus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface BusRepository extends JpaRepository<Bus, UUID> {
    Bus findBusByPlaca(String placa);
}