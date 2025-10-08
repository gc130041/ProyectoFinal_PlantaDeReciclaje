package com.oxo.planta_de_reciclaje.persistence.crud;

import com.oxo.planta_de_reciclaje.persistence.entity.Entrega;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CrudEntregaEntity extends CrudRepository<Entrega, Integer> {
    Optional<Entrega> findByIdEntrega(Integer idEntrega);
}
