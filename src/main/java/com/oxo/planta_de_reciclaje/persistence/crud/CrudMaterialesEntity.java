package com.oxo.planta_de_reciclaje.persistence.crud;

import com.oxo.planta_de_reciclaje.persistence.entity.Materiales;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudMaterialesEntity extends CrudRepository<Materiales, Integer> {
    Optional<Materiales> findByTipoMaterial(String tipoMaterial);
}