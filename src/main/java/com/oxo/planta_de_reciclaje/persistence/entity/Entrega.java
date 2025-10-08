package com.oxo.planta_de_reciclaje.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Entregas")
@Data
public class Entrega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idEntrega;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidadKg;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime fechaEntrega;

    @Column(nullable = false, length = 64)
    private String nombreProveedor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal compensacion;

    @ManyToOne
    @JoinColumn(name = "idMaterial", insertable = false, updatable = false)
    private Materiales material;
}
