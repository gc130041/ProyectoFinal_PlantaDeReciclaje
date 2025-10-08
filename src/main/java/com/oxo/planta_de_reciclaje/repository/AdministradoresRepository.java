package com.oxo.planta_de_reciclaje.repository;

import com.oxo.planta_de_reciclaje.dominio.dto.AdministradoresDto;

import java.util.List;
import java.util.Optional;

public interface AdministradoresRepository {
    List<AdministradoresDto> getAll();
    Optional<AdministradoresDto> getById(int idAdministrador);
    AdministradoresDto save(AdministradoresDto administradoresDto);
    void delete(int idAdministrador);
}