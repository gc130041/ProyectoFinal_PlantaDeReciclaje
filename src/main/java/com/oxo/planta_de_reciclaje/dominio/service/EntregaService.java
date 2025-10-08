package com.oxo.planta_de_reciclaje.dominio.service;

import com.oxo.planta_de_reciclaje.dominio.dto.EntregaDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModEntregaDto;
import com.oxo.planta_de_reciclaje.repository.EntregaRepository;
import com.oxo.planta_de_reciclaje.dominio.dto.MaterialesDto;
import com.oxo.planta_de_reciclaje.repository.MaterialesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class EntregaService {

    private final EntregaRepository repository;
    private final MaterialesRepository materialesRepository;

    @Autowired
    public EntregaService(EntregaRepository repository, MaterialesRepository materialesRepository) {
        this.repository = repository;
        this.materialesRepository = materialesRepository;
    }

    public List<EntregaDto> obtenerTodos() {
        return repository.obtenerTodos();
    }

    public EntregaDto buscarPorId(Integer idEntrega) {
        return repository.buscarPorId(idEntrega);
    }

    public EntregaDto guardarEntrega(EntregaDto dto) {
        MaterialesDto material = materialesRepository.buscarPorId(dto.idMaterial());
        BigDecimal precioPorKg = material.precioPorKg();
        BigDecimal cantidadKg = dto.cantidadKg();
        BigDecimal compensacionCalculada = precioPorKg.multiply(cantidadKg);
        dto = new EntregaDto(
                dto.idEntrega(),
                dto.cantidadKg(),
                dto.fechaEntrega(),
                dto.nombreProveedor(),
                compensacionCalculada,
                dto.idMaterial()
        );
        return repository.guardarEntrega(dto);
    }

    public EntregaDto modificarEntrega(Integer idEntrega, ModEntregaDto mod) {
        return repository.modificarEntrega(idEntrega, mod);
    }

    public void eliminarEntrega(Integer idEntrega) {
        repository.eliminarEntrega(idEntrega);
    }
}
