package com.example.Foro_Hub.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginUsuarioDTO {
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Correo electrónico inválido")
    private String email;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    // Getters y Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
