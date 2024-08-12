package com.javeriana.pontimovil.ponti_movil.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

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

    @Column(name = "calle", nullable = false, length = 20)
    private String calle;

    @Column(name = "carrera", nullable = false, length = 20)
    private String carrera;

    @Column(name = "numero", nullable = false, length = 20)
    private String numero;

    @Column(name = "localidad", nullable = false, length = 20)
    private String localidad;

    @Column(name = "barrio", nullable = false, length = 20)
    private String barrio;

}