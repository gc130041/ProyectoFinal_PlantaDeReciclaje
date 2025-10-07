package org.kinscript.Academy.dominio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
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