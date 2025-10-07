package com.oxo.planta_de_reciclaje.dominio.exception;

public class MaterialesExistsException extends RuntimeException {
    public MaterialesExistsException(String message) {
        super(message);
    }
}
