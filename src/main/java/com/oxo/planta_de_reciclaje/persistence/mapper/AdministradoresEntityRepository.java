package com.oxo.planta_de_reciclaje.persistence.mapper;

import com.oxo.planta_de_reciclaje.dominio.dto.AdministradoresDto;
import com.oxo.planta_de_reciclaje.dominio.exception.AdministradorExistsException;
import com.oxo.planta_de_reciclaje.dominio.exception.AdministradorNotExistsException;
import com.oxo.planta_de_reciclaje.repository.AdministradoresRepository;
import com.oxo.planta_de_reciclaje.persistence.crud.CrudAdministradoresEntity;
import com.oxo.planta_de_reciclaje.persistence.entity.Administradores;
import com.oxo.planta_de_reciclaje.persistence.mapper.AdministradoresMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AdministradoresEntityRepository implements AdministradoresRepository {

    private final CrudAdministradoresEntity CrudAdministradoresEntity;
    private final AdministradoresMapper administradoresMapper;

    public AdministradoresEntityRepository(CrudAdministradoresEntity CrudAdministradoresEntity, AdministradoresMapper administradoresMapper) {
        this.CrudAdministradoresEntity = CrudAdministradoresEntity;
        this.administradoresMapper = administradoresMapper;
    }

    @Override
    public List<AdministradoresDto> getAll() {
        List<Administradores> administradores = (List<Administradores>) CrudAdministradoresEntity.findAll();
        return administradoresMapper.toDto(administradores);
    }

    @Override
    public Optional<AdministradoresDto> getById(int idAdministrador) {
        return CrudAdministradoresEntity.findById(idAdministrador)
                .map(administradoresMapper::toDto);
    }

    @Override
    public AdministradoresDto save(AdministradoresDto administradoresDto) {
        if (CrudAdministradoresEntity.findByCorreoElectronico(administradoresDto.correoElectronico()).isPresent()) {
            throw new AdministradorExistsException(administradoresDto.correoElectronico());
        }
        Administradores administrador = administradoresMapper.toEntity(administradoresDto);
        return administradoresMapper.toDto(CrudAdministradoresEntity.save(administrador));
    }

    @Override
    public void delete(int idAdministrador) {
        if (!CrudAdministradoresEntity.existsById(idAdministrador)) {
            throw new AdministradorNotExistsException(idAdministrador);
        }
        CrudAdministradoresEntity.deleteById(idAdministrador);
    }
}