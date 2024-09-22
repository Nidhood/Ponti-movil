package com.javeriana.pontimovil.ponti_movil.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "rutas_estaciones", uniqueConstraints = @UniqueConstraint(columnNames = {"ruta_id", "estacion_id"}))
public class RutaEstacion {
    @EmbeddedId
    @JsonIgnore
    private RutaEstacionId id;

    @MapsId("rutaId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta ruta;

    @MapsId("estacionId")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estacion_id", nullable = false)
    private Estacion estacion;

    @Column(name = "orden", nullable = false)
    private Long orden;

    // Constructores:
    public RutaEstacion() {}

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