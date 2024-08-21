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
@Table(name = "tipos_usuarios")
public class TiposUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "tipo_usuario", nullable = false, length = 20)
    private String tipoUsuario;

    @Column(name = "permiso_id", nullable = false)
    private UUID permisoId;

    public TiposUsuario() {}

    public TiposUsuario(String tipoUsuario, UUID permisoId) {
        this.tipoUsuario = tipoUsuario;
        this.permisoId = permisoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TiposUsuario that = (TiposUsuario) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getTipoUsuario(), that.getTipoUsuario()) && Objects.equals(getPermisoId(), that.getPermisoId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTipoUsuario(), getPermisoId());
    }

    @Override
    public String toString() {
        return "TiposUsuario{" +
                "id=" + id +
                ", tipoUsuario='" + tipoUsuario + '\'' +
                ", permisoId=" + permisoId +
                '}';
    }
}