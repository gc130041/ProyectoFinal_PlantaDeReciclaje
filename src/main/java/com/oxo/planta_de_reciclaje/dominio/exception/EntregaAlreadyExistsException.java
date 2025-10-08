package com.oxo.planta_de_reciclaje.dominio.exception;

public class EntregaAlreadyExistsException extends RuntimeException {
    public EntregaAlreadyExistsException(Integer idEntrega) {
        super("La entrega con el identificador '" + idEntrega + "' ya existe.");
    }
}
