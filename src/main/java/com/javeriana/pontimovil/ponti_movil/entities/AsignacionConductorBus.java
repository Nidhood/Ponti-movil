package com.javeriana.pontimovil.ponti_movil.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "asignaciones_conductores_buses")
public class AsignacionConductorBus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "conductor_id", nullable = false)
    private Conductor conductor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @Column(name = "dia_semana", nullable = false, length = 20)
    private String diaSemana;

    public AsignacionConductorBus() {}

    public AsignacionConductorBus(Conductor conductor, Bus bus, String diaSemana) {
        this.conductor = conductor;
        this.bus = bus;
        this.diaSemana = diaSemana;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AsignacionConductorBus that = (AsignacionConductorBus) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getConductor(), that.getConductor()) && Objects.equals(getBus(), that.getBus()) && Objects.equals(getDiaSemana(), that.getDiaSemana());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getConductor(), getBus(), getDiaSemana());
    }

    @Override
    public String toString() {
        return "AsignacionConductorBus{" +
                "id=" + id +
                ", conductor=" + conductor +
                ", bus=" + bus +
                ", diaSemana='" + diaSemana + '\'' +
                '}';
    }
}