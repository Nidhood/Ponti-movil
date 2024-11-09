package com.javeriana.pontimovil.ponti_movil.dto.authentication;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LoginDTO {

    // Atributos:
    private String email;
    private String password;

    // Constructores:
    public LoginDTO() {
    }

    public LoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
