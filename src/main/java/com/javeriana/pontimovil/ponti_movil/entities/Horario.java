package com.javeriana.pontimovil.ponti_movil.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "horarios")
public class Horario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "dia", nullable = false, length = 20)
    private String dia;

    @Column(name = "hora_inicio", nullable = false)
    private LocalTime horaInicio;

    @Column(name = "hora_fin", nullable = false)
    private LocalTime horaFin;

    public Horario() {}

    public Horario(String dia, LocalTime horaInicio, LocalTime horaFin) {
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Horario horario = (Horario) o;
        return Objects.equals(getId(), horario.getId()) && Objects.equals(getDia(), horario.getDia()) && Objects.equals(getHoraInicio(), horario.getHoraInicio()) && Objects.equals(getHoraFin(), horario.getHoraFin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDia(), getHoraInicio(), getHoraFin());
    }

    @Override
    public String toString() {
        return "Horario{" +
                "id=" + id +
                ", dia='" + dia + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFin=" + horaFin +
                '}';
    }
}