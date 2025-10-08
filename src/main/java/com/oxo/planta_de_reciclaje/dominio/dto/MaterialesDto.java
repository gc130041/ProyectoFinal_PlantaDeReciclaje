package com.oxo.planta_de_reciclaje.dominio.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record MaterialesDto(
        Integer idMaterial,

        @NotBlank(message = "El tipo de material es obligatorio")
        @Size(max = 50, message = "El tipo de material no puede exceder los 50 caracteres")
        String tipoMaterial,

        @NotNull(message = "El precio por kilogramo es obligatorio")
        @PositiveOrZero(message = "El precio por kilogramo no puede ser negativo")
        BigDecimal precioPorKg
) {
}