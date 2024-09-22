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
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("gen_random_uuid()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "nombre", length = 20)
    private String nombre;

    @Column(name = "apellido", length = 20)
    private String apellido;

    @Column(name = "nombre_usuario", nullable = false, length = 20)
    private String nombreUsuario;

    @Column(name = "contrasena", nullable = false, length = 320)
    private String contrasena;

    @Column(name = "correo", nullable = false, length = 320)
    private String correo;

    @Column(name = "tipo_usuario", nullable = false)
    private String tipoUsuario;

    // Constructores:
    public Usuario() {}

    public Usuario(UUID id, String nombre, String apellido, String nombreUsuario, String contrasena, String correo, String tipoUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
    }

    // Métodos:
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(getId(), usuario.getId()) && Objects.equals(getNombre(), usuario.getNombre()) && Objects.equals(getApellido(), usuario.getApellido()) && Objects.equals(getNombreUsuario(), usuario.getNombreUsuario()) && Objects.equals(getContrasena(), usuario.getContrasena()) && Objects.equals(getCorreo(), usuario.getCorreo()) && Objects.equals(getTipoUsuario(), usuario.getTipoUsuario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getApellido(), getNombreUsuario(), getContrasena(), getCorreo(), getTipoUsuario());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", correo='" + correo + '\'' +
                ", tipoUsuario=" + tipoUsuario +
                '}';
    }
}