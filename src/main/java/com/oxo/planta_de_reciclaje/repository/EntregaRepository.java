package com.oxo.planta_de_reciclaje.repository;

import com.oxo.planta_de_reciclaje.dominio.dto.EntregaDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModEntregaDto;

import java.util.List;

public interface EntregaRepository {
    List<EntregaDto> obtenerTodos();

    EntregaDto buscarPorId(Integer idEntrega);

    EntregaDto guardarEntrega(EntregaDto dto);

    EntregaDto modificarEntrega(Integer idEntrega, ModEntregaDto mod);

    void eliminarEntrega(Integer idEntrega);
}
