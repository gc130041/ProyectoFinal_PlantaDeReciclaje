package com.oxo.planta_de_reciclaje.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Administradores")
public class Administradores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAdministrador")
    private Integer idAdministrador;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "correoElectronico", nullable = false, unique = true, length = 100)
    private String correoElectronico;

    @Column(name = "contrasena", nullable = false, length = 100)
    private String contrasena;
}