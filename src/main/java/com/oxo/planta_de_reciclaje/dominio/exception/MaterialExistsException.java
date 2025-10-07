package com.oxo.planta_de_reciclaje.dominio.exception;

public class MaterialExistsException extends RuntimeException {
    public MaterialExistsException(String tipoMaterial) {
        super("El material de tipo '" + tipoMaterial + "' ya existe.");
    }
}