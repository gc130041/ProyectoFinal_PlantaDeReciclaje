package com.oxo.planta_de_reciclaje.persistence.crud;

import com.oxo.planta_de_reciclaje.persistence.entity.Administradores;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudAdministradoresEntity extends CrudRepository<Administradores, Integer> {
    Optional<Administradores> findByCorreoElectronico(String correoElectronico);
}