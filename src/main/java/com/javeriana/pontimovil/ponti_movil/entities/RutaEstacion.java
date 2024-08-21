package com.javeriana.pontimovil.ponti_movil.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;

@Getter
@Setter
@Entity
@Table(name = "rutas_estaciones")
public class RutaEstacion {
    @Id
    @ColumnDefault("unique_rowid()")
    @Column(name = "rowid", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ruta_id", nullable = false)
    private Ruta ruta;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estacion_id", nullable = false)
    private Estacion estacion;

    public RutaEstacion() {}

    public RutaEstacion(Ruta ruta, Estacion estacion) {
        this.ruta = ruta;
        this.estacion = estacion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RutaEstacion that = (RutaEstacion) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getRuta(), that.getRuta()) && Objects.equals(getEstacion(), that.getEstacion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getRuta(), getEstacion());
    }

    @Override
    public String toString() {
        return "RutaEstacion{" +
                "id=" + id +
                ", ruta=" + ruta +
                ", estacion=" + estacion +
                '}';
    }
}