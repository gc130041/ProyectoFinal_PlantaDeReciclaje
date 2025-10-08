package com.oxo.planta_de_reciclaje.dominio.exception;

public class AdministradorExistsException extends RuntimeException {
    public AdministradorExistsException(String email) {
        super("El administrador con el correo '" + email + "' ya existe.");
    }
}