package com.javeriana.pontimovil.ponti_movil.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "rutas_estaciones")
public class RutaEstacion {
    @EmbeddedId
    private RutaEstacionId id;

    @MapsId("rutaId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta ruta;

    @MapsId("estacionId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "estacion_id", nullable = false)
    private Estacion estacion;

    @Column(name = "orden", nullable = false)
    private Long orden;


    // Constructores:
    public RutaEstacion() {}

    public RutaEstacion(Ruta ruta, Estacion estacion, Long orden) {
        this.ruta = ruta;
        this.estacion = estacion;
        this.orden = orden;
    }

    // Métodos:

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RutaEstacion that = (RutaEstacion) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getRuta(), that.getRuta()) && Objects.equals(getEstacion(), that.getEstacion()) && Objects.equals(getOrden(), that.getOrden());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRuta(), getEstacion(), getOrden());
    }

    @Override
    public String toString() {
        return "RutaEstacion{" +
                "id=" + id +
                ", ruta=" + ruta +
                ", estacion=" + estacion +
                ", orden=" + orden +
                '}';
    }
}