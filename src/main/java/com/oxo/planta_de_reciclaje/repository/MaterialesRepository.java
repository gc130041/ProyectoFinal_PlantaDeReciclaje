package com.oxo.planta_de_reciclaje.repository;

import com.oxo.planta_de_reciclaje.dominio.dto.MaterialesDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModMaterialesDto;

import java.util.List;

public interface MaterialesRepository {
    List<MaterialesDto> obtenerTodos();

    MaterialesDto buscarPorId(Integer idMaterial);

    MaterialesDto guardar(MaterialesDto materialesDto);

    MaterialesDto modificar(Integer idMaterial, ModMaterialesDto modMaterialesDto);

    void eliminar(Integer idMaterial);
}