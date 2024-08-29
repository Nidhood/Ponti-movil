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
@Table(name = "direcciones")
public class Direccion {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "tipo_via", nullable = false, length = 20)
    private String tipoVia;

    @Column(name = "numero_via", nullable = false, length = 20)
    private String numeroVia;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "localidad", nullable = false, length = 20)
    private String localidad;

    @Column(name = "barrio", nullable = false, length = 20)
    private String barrio;

    // Constructores:
    public Direccion() {}

    public Direccion(String calle, String carrera, String numero, String localidad, String barrio) {
        this.tipoVia = calle;
        this.numeroVia = carrera;
        this.numero = numero;
        this.localidad = localidad;
        this.barrio = barrio;
    }

    // Métodos:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direccion direccion = (Direccion) o;
        return Objects.equals(getId(), direccion.getId()) && Objects.equals(getTipoVia(), direccion.getTipoVia()) && Objects.equals(getNumeroVia(), direccion.getNumeroVia()) && Objects.equals(getNumero(), direccion.getNumero()) && Objects.equals(getLocalidad(), direccion.getLocalidad()) && Objects.equals(getBarrio(), direccion.getBarrio());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTipoVia(), getNumeroVia(), getNumero(), getLocalidad(), getBarrio());
    }

    @Override
    public String toString() {
        return "Direccion{" +
                "id=" + id +
                ", tipoVia='" + tipoVia + '\'' +
                ", numeroVia='" + numeroVia + '\'' +
                ", numero='" + numero + '\'' +
                ", localidad='" + localidad + '\'' +
                ", barrio='" + barrio + '\'' +
                '}';
    }
}