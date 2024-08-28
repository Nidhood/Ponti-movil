package com.javeriana.pontimovil.ponti_movil.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Embeddable
public class RutaEstacionId implements Serializable {

    @Column(name = "ruta_id", nullable = false)

    private UUID rutaId;

    @Column(name = "estacion_id", nullable = false)
    private UUID estacionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RutaEstacionId entity = (RutaEstacionId) o;
        return Objects.equals(this.rutaId, entity.rutaId) &&
                Objects.equals(this.estacionId, entity.estacionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rutaId, estacionId);
    }

}