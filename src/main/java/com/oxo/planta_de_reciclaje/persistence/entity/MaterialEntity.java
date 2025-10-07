package com.oxo.planta_de_reciclaje.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "Materiales")
@Data
public class MaterialEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMaterial;
    @Column(length = 150, nullable = false, unique = true)
    private String tipoMaterial;
    @Column(precision = 10, scale = 2)
    private BigDecimal precioPorKg;

}
