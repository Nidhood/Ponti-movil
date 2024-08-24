package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.Ruta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface RutaRepository extends JpaRepository<Ruta, UUID> {
    void deleteByCodigo(String codigo);
}