package com.oxo.planta_de_reciclaje.dominio.exception;

public class EntregaNotExistsException extends RuntimeException {
    public EntregaNotExistsException(Integer idEntrega) {
        super("No existe una entrega con el ID: " + idEntrega);
    }
}
