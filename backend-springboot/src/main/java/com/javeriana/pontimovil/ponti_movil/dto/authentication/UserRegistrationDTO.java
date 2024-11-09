package com.javeriana.pontimovil.ponti_movil.dto.authentication;

import com.javeriana.pontimovil.ponti_movil.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegistrationDTO {

    // Atributos:
    private String firstName;
    private String lastName;
    private String userName;
    private String password;
    private String email;
    private Role role;

    // Constructores:
    public UserRegistrationDTO() {
    }

    public UserRegistrationDTO(String firstName, String lastName, String userName, String password, String email, Role role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.role = role;
    }

}

