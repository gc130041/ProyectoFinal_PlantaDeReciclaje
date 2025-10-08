package com.oxo.planta_de_reciclaje.dominio.dto;

import jakarta.validation.constraints.NotBlank;

public record ModEntregaDto(
        @NotBlank(message = "El nombre en este campo es obligatorio")
        String nombreProveedor
) {
}
