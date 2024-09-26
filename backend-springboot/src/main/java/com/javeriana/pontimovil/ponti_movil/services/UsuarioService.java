package com.javeriana.pontimovil.ponti_movil.services;

import com.javeriana.pontimovil.ponti_movil.exceptions.UsuarioNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import com.javeriana.pontimovil.ponti_movil.entities.Usuario;
import com.javeriana.pontimovil.ponti_movil.repositories.UsuarioRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    // Repositorio:
    UsuarioRepository usuarioRepository;


    // Constructor:
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Métodos:
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerUsuarioPorId(UUID id) {
        return usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNotFoundException(id));
    }

    public void crearUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void actualizarUsuario(UUID id, Usuario usuario) {
        Usuario usuarioActual = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNotFoundException(id));
        usuarioActual.setNombre(usuario.getNombre());
        usuarioActual.setApellido(usuario.getApellido());
        usuarioActual.setNombreUsuario(usuario.getNombreUsuario());
        usuarioActual.setContrasena(usuario.getContrasena());
        usuarioActual.setCorreo(usuario.getCorreo());
        usuarioActual.setTipoUsuario(usuario.getTipoUsuario());
        usuarioRepository.save(usuarioActual);
    }

    public Usuario login(UUID id, Usuario usuario) {
        Usuario usuarioActual = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNotFoundException(id));
        if (!usuarioActual.getNombreUsuario().equals(usuario.getNombreUsuario())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Nombre de usuario incorrecto");
        } else if (!usuarioActual.getContrasena().equals(usuario.getContrasena())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Contraseña incorrecta");
        } else {
            return usuarioActual;
        }
    }

    public void eliminarUsuario(UUID id) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(()-> new UsuarioNotFoundException(id));
        usuarioRepository.delete(usuario);
    }


}
