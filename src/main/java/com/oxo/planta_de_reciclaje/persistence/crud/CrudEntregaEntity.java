package com.oxo.planta_de_reciclaje.persistence.crud;

import com.oxo.planta_de_reciclaje.persistence.entity.Entrega;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CrudEntregaEntity extends CrudRepository<Entrega, Integer> {
    Optional<Entrega> findByIdEntrega(Integer idEntrega);
    List<Entrega> findByFechaEntregaBetween(LocalDate fechaInicio, LocalDate fechaFin);
}