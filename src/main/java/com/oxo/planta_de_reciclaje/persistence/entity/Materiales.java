package com.oxo.planta_de_reciclaje.persistence.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Materiales")
public class Materiales {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idMaterial")
    private Integer idMaterial;

    @Column(name = "tipoMaterial", nullable = false, length = 50)
    private String tipoMaterial;

    @Column(name = "precioPorKg", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioPorKg;

    public Integer getIdMaterial() {
        return idMaterial;
    }

    public void setIdMaterial(Integer idMaterial) {
        this.idMaterial = idMaterial;
    }

    public String getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(String tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public BigDecimal getPrecioPorKg() {
        return precioPorKg;
    }

    public void setPrecioPorKg(BigDecimal precioPorKg) {
        this.precioPorKg = precioPorKg;
    }
}