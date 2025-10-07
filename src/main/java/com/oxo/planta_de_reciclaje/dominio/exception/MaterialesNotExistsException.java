package com.oxo.planta_de_reciclaje.dominio.exception;

public class MaterialesNotExistsException extends RuntimeException {
    public MaterialesNotExistsException(String message) {
        super(message);
    }
}
