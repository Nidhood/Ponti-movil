package com.javeriana.pontimovil.ponti_movil.services;

import com.javeriana.pontimovil.ponti_movil.dto.authentication.JwtAuthenticationResponse;
import com.javeriana.pontimovil.ponti_movil.dto.authentication.LoginDTO;
import com.javeriana.pontimovil.ponti_movil.dto.authentication.UserRegistrationDTO;
import com.javeriana.pontimovil.ponti_movil.entities.Role;
import com.javeriana.pontimovil.ponti_movil.entities.Usuario;
import com.javeriana.pontimovil.ponti_movil.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    // Repositorios:

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    // Métodos:
    public JwtAuthenticationResponse signup(UserRegistrationDTO request) {

        // Crear un nuevo usuario:
        Usuario usuario = new Usuario(
                request.getFirstName(),
                request.getLastName(),
                request.getUserName(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail(),
                request.getRole()
        );

        // Guardar el usuario en la base de datos:
        usuarioRepository.save(usuario);

        // Generar un token JWT:
        String jwt = jwtService.generateToken(usuario);

        // Retornar la respuesta:
        return new JwtAuthenticationResponse(jwt, usuario.getCorreo(), usuario.getTipoUsuario());
    }

    public JwtAuthenticationResponse login(LoginDTO request) {

        // Autenticar al usuario:
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        // Buscar al usuario en la base de datos:
        Usuario usuario = usuarioRepository.findByCorreo(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Generar un token JWT:
        String jwt = jwtService.generateToken(usuario);

        // Retornar la respuesta:
        return new JwtAuthenticationResponse(jwt, usuario.getCorreo(), usuario.getTipoUsuario());
    }
}
