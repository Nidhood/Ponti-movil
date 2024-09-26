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
@Table(name = "conductores")
public class Conductor {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "nombre", nullable = false, length = 20)
    private String nombre;

    @Column(name = "apellido", nullable = false, length = 20)
    private String apellido;

    @Column(name = "cedula", nullable = false, length = 20)
    private String cedula;

    @Column(name = "telefono", nullable = false, length = 20)
    private String telefono;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "direccion_id", nullable = false)
    private Direccion direccion;

    // Constructores:
    public Conductor() {}

    public Conductor(String nombre, String apellido, String cedula, String telefono, Direccion direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cedula = cedula;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    // Métodos:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conductor conductor = (Conductor) o;
        return Objects.equals(getId(), conductor.getId()) && Objects.equals(getNombre(), conductor.getNombre()) && Objects.equals(getApellido(), conductor.getApellido()) && Objects.equals(getCedula(), conductor.getCedula()) && Objects.equals(getTelefono(), conductor.getTelefono()) && Objects.equals(getDireccion(), conductor.getDireccion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getApellido(), getCedula(), getTelefono(), getDireccion());
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", cedula='" + cedula + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion=" + direccion +
                '}';
    }
}