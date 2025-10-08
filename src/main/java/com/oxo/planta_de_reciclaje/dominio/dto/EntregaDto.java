package com.oxo.planta_de_reciclaje.dominio.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EntregaDto(
    Integer idEntrega,
    @NotNull(message = "Ingresar la cantidad es obligatorio")
    @Positive(message = "La cantidad ingresada debe ser positiva")
    BigDecimal cantidadKg,
    LocalDateTime fechaEntrega,
    String nombreProveedor,
    BigDecimal compensacion,
    Integer idMaterial
) {
}
