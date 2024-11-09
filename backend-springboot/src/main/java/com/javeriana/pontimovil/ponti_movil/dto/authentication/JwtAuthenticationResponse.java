package com.javeriana.pontimovil.ponti_movil.dto.authentication;

import com.javeriana.pontimovil.ponti_movil.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JwtAuthenticationResponse {

    // Atributos:
    private String token;
    private String email;
    private Role role;

    // Constructores:
    public JwtAuthenticationResponse() {
    }

    public JwtAuthenticationResponse(String token, String email, Role role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

}
