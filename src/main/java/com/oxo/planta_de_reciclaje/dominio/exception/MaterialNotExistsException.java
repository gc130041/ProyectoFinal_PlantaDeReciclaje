package com.oxo.planta_de_reciclaje.dominio.exception;

public class MaterialNotExistsException extends RuntimeException {
    public MaterialNotExistsException(Integer idMaterial) {
        super("No existe un material con el ID: " + idMaterial);
    }
}