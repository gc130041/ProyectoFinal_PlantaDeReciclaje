package com.oxo.planta_de_reciclaje.dominio.service;

import com.oxo.planta_de_reciclaje.dominio.dto.EntregaDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModEntregaDto;
import com.oxo.planta_de_reciclaje.persistence.mapper.EntregaMapper;
import com.oxo.planta_de_reciclaje.persistence.crud.CrudEntregaEntity;
import com.oxo.planta_de_reciclaje.persistence.entity.Entrega;
import com.oxo.planta_de_reciclaje.repository.EntregaRepository;
import com.oxo.planta_de_reciclaje.dominio.dto.MaterialesDto;
import com.oxo.planta_de_reciclaje.repository.MaterialesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntregaService {

    private final EntregaRepository repository;
    private final CrudEntregaEntity crudRepository;
    private final MaterialesRepository materialesRepository;
    private final EntregaMapper mapper;

    @Autowired
    public EntregaService(EntregaRepository repository, CrudEntregaEntity crudRepository, MaterialesRepository materialesRepository, EntregaMapper mapper) {
        this.repository = repository;
        this.crudRepository = crudRepository;
        this.materialesRepository = materialesRepository;
        this.mapper = mapper;
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


    public List<EntregaDto> generarReporteDiario(LocalDate fecha) {
        List<Entrega> entregas = crudRepository.findByFechaEntregaBetween(fecha, fecha);
        return mapper.toDto(entregas);
    }

    public List<EntregaDto> generarReporteSemanal(LocalDate fecha) {
        LocalDate inicioSemana = fecha.with(DayOfWeek.MONDAY);
        LocalDate finSemana = fecha.with(DayOfWeek.SUNDAY);

        List<Entrega> entregas = crudRepository.findByFechaEntregaBetween(inicioSemana, finSemana);

        return mapper.toDto(entregas);
    }
}