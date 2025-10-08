package com.oxo.planta_de_reciclaje.dominio.exception;

public class AdministradorNotExistsException extends RuntimeException {
    public AdministradorNotExistsException(Integer id) {
        super("No existe un administrador con el ID: " + id);
    }
}