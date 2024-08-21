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
@Table(name = "permisos")
public class Permiso {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "permiso", nullable = false, length = 20)
    private String permiso;

    public Permiso() {}

    public Permiso(String permiso) {
        this.permiso = permiso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Permiso permiso1 = (Permiso) o;
        return Objects.equals(getId(), permiso1.getId()) && Objects.equals(getPermiso(), permiso1.getPermiso());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPermiso());
    }

    @Override
    public String toString() {
        return "Permiso{" +
                "id=" + id +
                ", permiso='" + permiso + '\'' +
                '}';
    }
}