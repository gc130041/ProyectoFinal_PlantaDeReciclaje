package com.oxo.planta_de_reciclaje.persistence;

import com.oxo.planta_de_reciclaje.dominio.dto.MaterialesDto;
import com.oxo.planta_de_reciclaje.dominio.dto.ModMaterialesDto;
import com.oxo.planta_de_reciclaje.dominio.exception.MaterialExistsException;
import com.oxo.planta_de_reciclaje.dominio.exception.MaterialNotExistsException;
import com.oxo.planta_de_reciclaje.repository.MaterialesRepository;
import com.oxo.planta_de_reciclaje.persistence.crud.CrudMaterialesEntity;
import com.oxo.planta_de_reciclaje.persistence.entity.Materiales;
import com.oxo.planta_de_reciclaje.persistence.mapper.MaterialesMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MaterialesEntityRepository implements MaterialesRepository {

    private final CrudMaterialesEntity crudMaterialesEntity;
    private final MaterialesMapper materialesMapper;

    public MaterialesEntityRepository(CrudMaterialesEntity crudMaterialesEntity, MaterialesMapper materialesMapper) {
        this.crudMaterialesEntity = crudMaterialesEntity;
        this.materialesMapper = materialesMapper;
    }

    @Override
    public List<MaterialesDto> obtenerTodos() {
        return materialesMapper.toDto((List<Materiales>) crudMaterialesEntity.findAll());
    }

    @Override
    public MaterialesDto buscarPorId(Integer idMaterial) {
        return crudMaterialesEntity.findById(idMaterial)
                .map(materialesMapper::toDto)
                .orElseThrow(() -> new MaterialNotExistsException(idMaterial));
    }

    @Override
    public MaterialesDto guardar(MaterialesDto materialesDto) {
        if (crudMaterialesEntity.findByTipoMaterial(materialesDto.tipoMaterial()).isPresent()) {
            throw new MaterialExistsException(materialesDto.tipoMaterial());
        }
        Materiales material = materialesMapper.toEntity(materialesDto);
        return materialesMapper.toDto(crudMaterialesEntity.save(material));
    }

    @Override
    public MaterialesDto modificar(Integer idMaterial, ModMaterialesDto modMaterialesDto) {
        Materiales material = crudMaterialesEntity.findById(idMaterial)
                .orElseThrow(() -> new MaterialNotExistsException(idMaterial));

        material.setTipoMaterial(modMaterialesDto.tipoMaterial());
        material.setPrecioPorKg(modMaterialesDto.precioPorKg());

        return materialesMapper.toDto(crudMaterialesEntity.save(material));
    }

    @Override
    public void eliminar(Integer idMaterial) {
        if (!crudMaterialesEntity.existsById(idMaterial)) {
            throw new MaterialNotExistsException(idMaterial);
        }
        crudMaterialesEntity.deleteById(idMaterial);
    }
}