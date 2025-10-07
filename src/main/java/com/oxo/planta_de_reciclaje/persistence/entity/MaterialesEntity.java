package com.oxo.planta_de_reciclaje.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "materiales")
@Data
public class MaterialesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idMaterial;
    @Column(length = 150, unique = true, nullable = false)
    private String tipoMaterial;
    @Column(precision = 2, scale = 10)
    private BigDecimal precioPorKg;

}
