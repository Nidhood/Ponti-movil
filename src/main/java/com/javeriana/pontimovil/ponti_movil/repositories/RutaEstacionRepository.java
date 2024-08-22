package com.javeriana.pontimovil.ponti_movil.repositories;

import com.javeriana.pontimovil.ponti_movil.entities.RutaEstacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RutaEstacionRepository extends JpaRepository<RutaEstacion, Long> {
    List<RutaEstacion> findAllByRutaCodigo(String ruta_codigo);
}